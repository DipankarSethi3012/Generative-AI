package com.infra.second;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AIController {

    private final ChatClient openAIChatCLient;
    private final ChatClient geminiChatClient;

    public AIController(ChatClient openAIChatCLient, ChatClient geminiChatClient) {
        this.openAIChatCLient = openAIChatCLient;
        this.geminiChatClient = geminiChatClient;
    }

    @GetMapping("/openai/chat")
    public String openAIChat(@RequestParam String msg) {
        return openAIChatCLient.prompt(msg).call().content();
    }

    @GetMapping("/google/chat")
    public String geminiAIChat(@RequestParam String msg) {
        return geminiChatClient.prompt(msg).call().content();
    }
}
