package com.gy.springcloud.service;

import com.gy.springcloud.service.impl.HystrixOrderFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "cloud-payment-hystrix-service",fallback = HystrixOrderFallbackService.class)
public interface HystrixOrderService {

    @GetMapping(value = "/payment/hytrix/ok/{id}")
    String ok(@PathVariable("id") Integer id);

    @GetMapping(value = "/payment/hytrix/timeout/{id}")
    String timeout(@PathVariable("id") Integer id);


}
