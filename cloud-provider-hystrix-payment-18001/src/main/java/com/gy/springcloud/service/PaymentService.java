package com.gy.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PaymentService {


    /**
     * 正常访问 ，肯定OK
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id){
        return "线程池 " + Thread.currentThread().getName() +"paymentInfo_OK, id :" + id +"\t" +"^_^ 哈哈哈~" ;
    }


    /**
     * 正常访问 ，肯定OK
     * @param id
     * @return
     */

    @HystrixCommand(fallbackMethod = "fallback",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_Timeout(Integer id){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池 " + Thread.currentThread().getName() +"paymentInfo_OK, id :" + id +"\t" +"^_^ 耗时三秒种..." ;
    }


    public String fallback(Integer id){
        return "线程池 " + Thread.currentThread().getName() +"fallback, id :"  +"\t" +"*^* 耗时三秒种..." ;
    }

    //==服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled" ,value = "true"),      //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold" ,value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds" ,value = "10000"),  //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage" ,value = "60")      //失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(Integer id){
        if(id  < 0){
            throw new RuntimeException("*********id 不能为负数");
        }
       String newid  = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t" + "调用成功,流水号：" + newid;
    }

    public String paymentCircuitBreaker_fallback( Integer id){
        return "id 不能负数，请稍后在试，服务熔断o(╥﹏╥)o , id: " + id;
    }


}
