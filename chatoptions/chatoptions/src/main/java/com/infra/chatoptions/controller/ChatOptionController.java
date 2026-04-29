package com.infra.chatoptions.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatOptionController {

    private final ChatClient chatClient;

    public ChatOptionController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/query")
    public ChatOptionResponse getResponse(@RequestParam String query) {

        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
                .model("gpt-4o-mini")
                .temperature(0.6)
                .maxTokens(200)
                .build();

        String ans = chatClient
                    .prompt(query)
                    .user(query)
                .options(openAiChatOptions)
                    .call()
                    .content();

        System.out.println(ans);

            return new ChatOptionResponse(query, ans, openAiChatOptions.getModel(), openAiChatOptions.getMaxTokens());
    }


//    @GetMapping("/query")
//    public String askWithDefaultOptions(@RequestParam String query) {
//
//        return chatClient
//                .prompt()
//                .user(query)
//                .call()
//                .content();
//
//    }

    @GetMapping("/default/query")
    public String askWithoutDefaultOptions(@RequestParam String query) {

        return chatClient
                .prompt(query)
                .user(query)
                .call()
                .content();

    }

    public record ChatOptionResponse(String query, String ans, String model, int tokensUsed) {}

}
