package ru.opgmap.opgmap_comment_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableEurekaClient
@EnableHypermediaSupport(type = {
        EnableHypermediaSupport.HypermediaType.HAL_FORMS,
        EnableHypermediaSupport.HypermediaType.COLLECTION_JSON
})
public class OpgmapCommentServiceApplication {

    @Autowired
    StreamBridge streamBridge;

    public static void main(String[] args) {
        SpringApplication.run(OpgmapCommentServiceApplication.class, args);
    }

}
