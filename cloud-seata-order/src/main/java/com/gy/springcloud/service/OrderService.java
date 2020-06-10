package com.gy.springcloud.service;

import com.gy.springcloud.entity.Order;

public interface OrderService {

    void create(Order order);

    String getIDBySnowFlake();
}
