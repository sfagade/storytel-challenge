package com.app.storytel.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.app.storytel.challenge")
public class StorytelMessagesApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorytelMessagesApplication.class, args);
    }

}
