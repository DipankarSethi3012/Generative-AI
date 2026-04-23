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
    private final ChatClient grokChatClient;

    public AIController(ChatClient openAIChatCLient, ChatClient geminiChatClient, ChatClient grokChatClient) {
        this.openAIChatCLient = openAIChatCLient;
        this.geminiChatClient = geminiChatClient;
        this.grokChatClient = grokChatClient;
    }

    @GetMapping("/openai/chat")
    public String openAIChat(@RequestParam String msg) {
        return openAIChatCLient.prompt(msg).call().content();
    }

    @GetMapping("/google/chat")
    public String geminiAIChat(@RequestParam String msg) {
        return geminiChatClient.prompt(msg).call().content();
    }

    @GetMapping("/grok/chat")
    public String grokAIChat(@RequestParam String msg) {
        return grokChatClient.prompt(msg).call().content();
    }

}

//we are using two ai models