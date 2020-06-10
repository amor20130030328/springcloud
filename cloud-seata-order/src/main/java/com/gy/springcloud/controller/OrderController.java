package com.gy.springcloud.controller;

import com.gy.springcloud.entities.CommonResult;
import com.gy.springcloud.entity.Order;
import com.gy.springcloud.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order/create")
    public CommonResult create(Order order){
        orderService.create(order);
        return new CommonResult(200 , "订单创建成功");
    }

    @GetMapping("/snowflake")
    public String snowflake(Order order){
        return  orderService.getIDBySnowFlake();
    }


}
