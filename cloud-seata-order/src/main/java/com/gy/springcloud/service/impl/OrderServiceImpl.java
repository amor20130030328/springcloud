package com.gy.springcloud.service.impl;

import com.gy.springcloud.config.IdGeneratorSnowFlake;
import com.gy.springcloud.dao.OrderDao;
import com.gy.springcloud.entity.Order;
import com.gy.springcloud.service.AccountService;
import com.gy.springcloud.service.OrderService;
import com.gy.springcloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    @Override
    public void create(Order order) {
        log.info("--------> 开始新建订单");
        orderDao.create(order);
        log.info("---------> 订单微服务开始调用库存，做扣减 count");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("---------> 订单微服务开始调用库存，做扣减  end");
        log.info("--------->  订单微服务开始调用账户,做扣减 money");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("--------->  订单微服务开始调用账户,做扣减 end");

        //4.修改订单状态，从 0 -> 1 ，1代表已经完成
        log.info("------> 修改订单状态开始");
        orderDao.update(order.getUserId(),0);
        log.info("------->  修改订单状态结束 ");

        log.info(" ------> 下订单结束了，☺ 哈哈 ");
    }

    @Autowired
    private IdGeneratorSnowFlake idGeneratorSnowFlake;

    public String getIDBySnowFlake(){
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for(int i = 1 ; i <= 20 ; i++){
            threadPool.submit(()->{
                System.out.println(idGeneratorSnowFlake.snowflakeId());
            });
        }
        threadPool.shutdown();
        return "hello snowFlake";
    }

}
