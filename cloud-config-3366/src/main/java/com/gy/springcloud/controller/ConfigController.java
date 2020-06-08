package com.gy.springcloud.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope      //curl -X POST "http://localhost:3366/actuator/refresh"   需要运维人员发送POST请求刷新3355
                    //curl -X POST "http://localhost:3344/actuator/bus-refresh/config-client:3366"   精准触发更新配置 config-client:3366
public class ConfigController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo(){
        return configInfo;
    }

}
