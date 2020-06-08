package com.gy.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA(){
       /* try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return "------testA";
    }

    @GetMapping("/testB")
    public String testB(){
        log.info(Thread.currentThread().getName() +"\t " + "testB" );
        return "------testB";
    }

    @GetMapping("/testD")
    public String testD(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info(Thread.currentThread().getName() +"\t " + "testD" );
        return "------testB";
    }

    @GetMapping("/testE")
    public String testE(){

        log.info(Thread.currentThread().getName() +"\t  异常比例" + "testE" );
        int a = 10 / 0 ;

        return "------testE";
    }

    @GetMapping("/testF")
    public String testF(){

        log.info(Thread.currentThread().getName() +"\t 测试异常数" + "testF" );
        int a = 10 / 0 ;

        return "------testF";
    }


    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false) String p1,
                            @RequestParam(value = "p2",required = false) String p2){
        int a = 10 / 0 ;
        return "--------testHotKey ";
    }

    public String deal_testHotKey(String p1, String p2, BlockException ex){
        log.info("p1:"+ p1 +",p2" + p2 + ",ex :" + ex.getMessage());
        return "---------deal_testHotKey,o(╥﹏╥)o --------";    //sentinel 系统默认的提示，Blocked  Sentinel (flow limiting)
    }


}
