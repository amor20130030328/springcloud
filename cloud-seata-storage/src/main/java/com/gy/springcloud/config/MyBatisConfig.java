package com.gy.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.gy.springcloud.dao"})
public class MyBatisConfig {
}
