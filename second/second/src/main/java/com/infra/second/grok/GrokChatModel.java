package com.infra.second.grok;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.Generation;

import java.util.List;

@Component
public class GrokChatModel implements ChatModel {

    private final WebClient webClient;

    @Value("${grok.api.key}")
    private String apiKey;

    @Value("${grok.api.url}")
    private String apiUrl;

    @Value("${grok.model}")
    private String model;

    public GrokChatModel(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    @Override
    public ChatResponse call(Prompt prompt) {

        String userInput = prompt.getInstructions().getFirst().getText();

        GrokRequest request = new GrokRequest();
        request.model = model;
        request.messages = List.of(new GrokRequest.Message("user", userInput));

        GrokResponse response;
        try {
            response = webClient.post()
                    .uri(apiUrl)
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(GrokResponse.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Grok API failed: " + e.getMessage());
        }



        String output = response.choices.get(0).message.content;

        return ChatResponse.builder()
                .generations(List.of(
                        new Generation(new AssistantMessage(output))
                ))
                .build();
    }
}
