package com.infra.chatoptions.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatOptionConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultSystem("""
                        You are a professional AI assistant that behaves professionally in only answering
                        Java Spring Boot Questions. Maintain conversation short and crisp.
                        Don't give answers to any other questions than Java.
                        For the anser to the other questions say I officially authorize to answer only Java related questions.
                        """)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }
}
