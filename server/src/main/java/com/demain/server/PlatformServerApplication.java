package com.demain.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author demain_lee
 * @since 2024/1/30
 */
@SpringBootApplication
@MapperScan("com.demain.server.admin.mapper")
public class PlatformServerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PlatformServerApplication.class, args);
    }
    
}
