package com.gy.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.gy.springcloud.entities.CommonResult;
import com.gy.springcloud.entities.Payment;
import com.gy.springcloud.handler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public CommonResult byResource(){
        return new CommonResult(200,"按资源名称限流测试OK",new Payment(2020L,"serial001"));
    }

    public CommonResult handleException(BlockException ex){
        return new CommonResult(444,ex.getClass().getCanonicalName() +"\t 服务不可用");
    }

    @GetMapping("/rateLimit/byurl")
    @SentinelResource(value = "byurl")
    public CommonResult byUrl(){
        return new CommonResult(200,"按url限流测试OK",new Payment(2020L,"serial002"));
    }


    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler" ,blockHandlerClass = CustomerBlockHandler.class ,blockHandler ="handlerException2")
    public CommonResult customerBlockHandler(){
        return new CommonResult(200,"按url限流测试OK",new Payment(2020L,"serial002"));
    }
}
