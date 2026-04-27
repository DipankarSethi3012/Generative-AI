package com.infra.advisors.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/logger")
public class AiController {

    private final ChatClient chatClient;


    public AiController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/query")
    public String getResponse(@RequestParam String query) {

        return chatClient
                .prompt(query)
                .user(query)
                .call()
                .content();

    }
}
