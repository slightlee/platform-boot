package com.demain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PlatformBootApplicationTests.class)
class PlatformBootApplicationTests {

    @Test
    void contextLoads() {

    }


    /**
     * 生成密码
     */
//    @Test
//    void generatePassword() {
//        System.out.println(new BCryptPasswordEncoder().encode("123456"));
//    }

    /**
     * 校验密码
     */
//    @Test
//    void PasswordMatch() {
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        boolean result = encoder.matches("123456", "$2a$10$kQMmYcvMzXNL/M0K3HuPj.b4VQebrixCNcSAL9Q5KKDatYJY8EIaO");
//        System.out.println(result);
//    }

}
