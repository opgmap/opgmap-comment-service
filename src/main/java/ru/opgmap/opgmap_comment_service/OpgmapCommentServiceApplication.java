package ru.opgmap.opgmap_comment_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OpgmapCommentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpgmapCommentServiceApplication.class, args);
    }

}
