package com.ai.conversational.controller;

import com.ai.conversational.service.CustomPostgresChatMemory;
import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;
//import static org.springframework.ai.chat.memory.ChatMemory.CON
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClient, CustomPostgresChatMemory customPostgresChatMemory) {
        this.chatClient = chatClient
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(customPostgresChatMemory).build())
                .build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String chatId, @RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .advisors(advisor -> advisor.param(CONVERSATION_ID, chatId)
                        .param("chat_memory_response_size", 10)).call()
                .content();
    }
}
