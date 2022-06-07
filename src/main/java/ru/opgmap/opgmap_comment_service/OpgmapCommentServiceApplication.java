package ru.opgmap.opgmap_comment_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableEurekaClient
@EnableHypermediaSupport(type = {
        EnableHypermediaSupport.HypermediaType.HAL_FORMS,
        EnableHypermediaSupport.HypermediaType.COLLECTION_JSON
})
public class OpgmapCommentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpgmapCommentServiceApplication.class, args);
    }

}
