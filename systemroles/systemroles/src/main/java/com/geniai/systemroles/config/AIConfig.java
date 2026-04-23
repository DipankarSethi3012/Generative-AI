package com.geniai.systemroles.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClient) {
        return chatClient
                .defaultSystem("You are a java Developer, that interacts with only Java Question") //Defining the defaultSystem Role
                .defaultUser("How can you help me?") //Default User Prompt
                .build();
    }
    //Making a bean so that Spring automatically Injects it
}


//In Spring AI defaults are preconfigured instructions and behavior that can be applied to chatClient
//Once, Every request made through the client automatically have this
//Ex: DefaultSystem()
//Ex: defaultUser()
//defaultAdvisors()
//defaultFunctions()
//defaultOptions()