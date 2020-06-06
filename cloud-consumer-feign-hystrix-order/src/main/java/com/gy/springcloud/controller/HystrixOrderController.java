package com.gy.springcloud.controller;

import com.gy.springcloud.service.HystrixOrderService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "global_fallback")
public class HystrixOrderController {

    @Resource
    private HystrixOrderService hystrixOrderservice;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/consumer/payment/hytrix/timeout/{id}")
   /* @HystrixCommand(fallbackMethod = "fallback",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })*/
    @HystrixCommand
    public String timeout(@PathVariable("id") Integer id){
        String res = hystrixOrderservice.timeout(id);
        int a = 10 / 0 ;
        log.info("********result" + res);
        return res;
    }


    public String fallback(Integer id){
        return "客户端服务降级:对方支付系统繁忙请10秒后再试或者自己运行出错请检查自己";
    }

    public String global_fallback(){
        return "全局fallback";
    }

    @GetMapping(value = "/consumer/payment/hytrix/ok/{id}")
    public String ok(@PathVariable("id") Integer id){

        String res = hystrixOrderservice.ok(id);
        log.info("********result" + res);
        return res;
    }

}
