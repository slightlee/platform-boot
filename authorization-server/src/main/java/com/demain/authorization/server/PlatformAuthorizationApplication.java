package com.demain.authorization.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author demain_lee
 * @since 2024/3/13
 */
@SpringBootApplication
@MapperScan("com.demain.authorization.server.customize.mapper")
public class PlatformAuthorizationApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PlatformAuthorizationApplication.class, args);
    }
}
