package com.hocSpring.bai_01.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class hello {
    @GetMapping("/alo")
    String sayHello() {
        return "Hello Spring Boot";
    }

    @GetMapping("/")
    String sayHello1() {
        return "Hello Spring Boot 1";
    }
}
