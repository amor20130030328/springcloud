package com.gy.springcloud.service;

import com.gy.springcloud.entities.CommonResult;
import com.gy.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "nacos-payment-provider",fallback = PaymentCallbackService.class)
public interface PaymentService {

    @GetMapping(value = "/paymentSQL/{id}")
     CommonResult<Payment> paymentSQL(@PathVariable("id") Long id);
}
