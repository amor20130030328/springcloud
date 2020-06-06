package com.gy.springcloud.service.impl;

import com.gy.springcloud.service.HystrixOrderService;
import org.springframework.stereotype.Component;

@Component
public class HystrixOrderFallbackService implements HystrixOrderService {
    @Override
    public String ok(Integer id) {
        return "HystrixOrderFallbackService--ok---o(╥﹏╥)o";
    }

    @Override
    public String timeout(Integer id) {
        return "HystrixOrderFallbackService--timeout---o(╥﹏╥)o";
    }
}
