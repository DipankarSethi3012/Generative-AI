package com.infra.chatoptions.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/text")
public class TextToObjectController {

    private final ChatClient chatClient;

    public TextToObjectController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("/query")
    public CourseInfo getResponse(@RequestParam String query) {

        return chatClient
                .prompt()
                .user(query)
                .call()
                .entity(CourseInfo.class);
    }

    public record CourseInfo(String title, String level, Integer price, Integer durationDays, List<String> topics) {}
}
