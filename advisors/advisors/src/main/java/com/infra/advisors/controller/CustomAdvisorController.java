package com.infra.advisors.controller;

import com.infra.advisors.advisor.CustomAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/advisor")
public class CustomAdvisorController {

    private final ChatClient chatClient;

    public CustomAdvisorController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("")
    public CustomAdvisorResponse getResponse(@RequestParam String msg) {
        ChatClientResponse response = chatClient.prompt()
                .user(msg)
                .advisors(new CustomAdvisor())
                .call()
                .chatClientResponse();

        return new CustomAdvisorResponse(response.chatResponse().getResult().getOutput().getText(), "CustomeAdvisorUsed" );
    }

    public record CustomAdvisorResponse(String name, String advisorUsed) {

    }
}
