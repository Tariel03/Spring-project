package com.example.springproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class SpringProjectApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringProjectApplication.class, args);
    }
//    @GetMapping("/hello")
//    public String sayhello(@RequestParam(value = "myname",defaultValue = "Emir")String name){
//        return String.format("Hello%s!",name);
//    }

}
