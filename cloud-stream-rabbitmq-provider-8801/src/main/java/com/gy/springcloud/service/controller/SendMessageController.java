package com.gy.springcloud.service.controller;

import com.gy.springcloud.service.IMesssageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMessageController {

    @Resource
    private IMesssageProvider iMesssageProvider;

    @GetMapping(value = "/sendMessage")
    public String sendMessage(){
        return iMesssageProvider.send();
    }


}
