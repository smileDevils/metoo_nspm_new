package com.metoo.monitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.metoo.monitor.core.mapper")
@SpringBootApplication
public class MetooNspmNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetooNspmNewApplication.class, args);
    }

}
