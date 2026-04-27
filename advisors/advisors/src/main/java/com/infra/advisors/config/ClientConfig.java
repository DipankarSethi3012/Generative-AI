package com.infra.advisors.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public ChatClient chatCLient(ChatClient.Builder chatClient) {
        return chatClient.defaultSystem("You are a java Spring Boot developer. You are authorize to answer" +
                " only java Spring Boot questions. Don't answer any other questions. If it's possible to explain in the context of java explain it." +
                "Ex: If the user asks to explain the joke - Give in context of Java. If it's not possible to explain in the context of Java  just say -> I am not authorize to answer this question. Thank you for your time")
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }
}
