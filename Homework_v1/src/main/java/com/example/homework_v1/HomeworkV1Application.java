package com.example.homework_v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HomeworkV1Application {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkV1Application.class, args);
    }

}
