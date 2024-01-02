package com.demain.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.demain.server.*.mapper.**"})
public class PlatformBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformBootApplication.class, args);
    }

}
