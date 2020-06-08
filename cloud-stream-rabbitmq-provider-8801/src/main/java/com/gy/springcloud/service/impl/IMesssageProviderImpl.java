package com.gy.springcloud.service.impl;

import com.gy.springcloud.service.IMesssageProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;


//定义消息的推送管道
@EnableBinding(Source.class)    //@EnableBinding 指信道channel和 exchange 绑定在一起
public class IMesssageProviderImpl implements IMesssageProvider {

    @Resource
    private MessageChannel output;   //消息发送管道

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("********************serial : " + serial );
        return null;
    }
}
