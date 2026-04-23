package com.infra.second;

import com.infra.second.grok.GrokChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean(name = "openAIChatCLient")
    ChatClient openAIChatClient(OpenAiChatModel openAiChatModel) {
        return ChatClient.create(openAiChatModel);
    }

    @Bean(name = "geminiChatClient")
    ChatClient geminiChatClient(GoogleGenAiChatModel googleGenAiChatModel) {
        return ChatClient.create(googleGenAiChatModel);
    }

    @Bean(name = "grokChatClient")
    ChatClient grokChatClient(GrokChatModel grokChatModel) {
        return ChatClient.create(grokChatModel);
    }
}
