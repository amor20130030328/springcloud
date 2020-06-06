package com.gy.springcloud.controller;

import com.gy.springcloud.entities.CommonResult;
import com.gy.springcloud.entities.Payment;
import com.gy.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentHytrixController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/payment/hytrix/timeout/{id}")
    public String ok(@PathVariable("id") Integer id){

        String res = paymentService.paymentInfo_Timeout(id);
        log.info("********result" + res);
        return res;
    }

    @GetMapping(value = "/payment/hytrix/ok/{id}")
    public String timeout(@PathVariable("id") Integer id){

        String res = paymentService.paymentInfo_OK(id);
        log.info("********result" + res);
        return res;
    }


    @GetMapping(value = "/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){

        String res = paymentService.paymentCircuitBreaker(id);
        log.info("********result" + res);
        return res;
    }
}
