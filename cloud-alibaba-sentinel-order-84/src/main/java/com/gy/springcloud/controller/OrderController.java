package com.gy.springcloud.controller;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.gy.springcloud.entities.CommonResult;
import com.gy.springcloud.entities.Payment;
import com.gy.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    public static final String SERVER_UTL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private PaymentService paymentService;

    @RequestMapping(value = "/consumer/fallback/{id}")
    //@SentinelResource(value = "fallback",fallback = "handlerFallback") //fallback只负责业务异常
    //@SentinelResource(value = "fallback",blockHandler = "blockHandler") //fallback只负责业务异常
    @SentinelResource(value = "fallback",fallback = "handlerFallback",blockHandler = "blockHandler",exceptionsToIgnore = {IllegalArgumentException.class}) //fallback只负责业务异常
    public CommonResult<Payment> fallback(@PathVariable Long id){
        CommonResult result = restTemplate.getForObject(SERVER_UTL + "/paymentSQL/" + id, CommonResult.class, id);
        if(id == 4){
            throw new IllegalArgumentException("IllegalArgumentException , 非法参数异常.........");
        }else if(result.getData() == null){
            throw new NullPointerException("NullPointerException");
        }
        return result;
    }

    @RequestMapping(value = "/consumer/paymentSQL/{id}")
    @SentinelResource(value = "fallback",fallback = "handlerFallback",blockHandler = "blockHandler") //fallback只负责业务异常
    public CommonResult<Payment> paymentSQL(@PathVariable Long id){
        CommonResult result = paymentService.paymentSQL(id);
        if(id == 4){
            throw new IllegalArgumentException("IllegalArgumentException , 非法参数异常.........");
        }else if(result.getData() == null){
            throw new NullPointerException("NullPointerException");
        }
        return result;
    }

    //本例是fallback
    public CommonResult handlerFallback(@PathVariable Long id , Throwable e){

        Payment payment = new Payment(id, "null");
        return new CommonResult<>(444,"兜底异常handlerFallback ，exception内容" + e.getMessage(),payment);
    }

    //本例是fallback
    public CommonResult blockHandler(@PathVariable Long id ,BlockException e){

        Payment payment = new Payment(id, "null");
        return new CommonResult<>(444,"blockHandler-sentinel限流，无此流水 ，blockException" + e.getMessage(),payment);
    }


}
