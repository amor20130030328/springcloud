package com.gy.springcloud.service;

import com.gy.springcloud.entities.CommonResult;
import com.gy.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentCallbackService implements PaymentService {
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(444,"服务降级返回,----PaymentFallbackService",new Payment(id,"errorService"));
    }
}
