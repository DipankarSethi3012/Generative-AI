package com.geniai.systemroles.controller;

//import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class AIController {

    private final ChatClient chatClient;

    @Value("classpath:/templates/template.st")
    Resource template;



    public AIController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/api")
    public String getResponse(@RequestParam String studentName, String query) {
        System.out.println(template);
        //Ex: Of dynamic Prompt Template, {} -> passed value by the client using this template
//        String template = """
//                Prompt is defined using prompt Template,
//                {studentName}, {query}
//                Write a response, A very large prompt
//                Hello from there, How are you, Learning the Spring AI.
//                """;
//The bracket prt defines the dynamic prompt, we can pass it calling the ai model
//        return chatClient.prompt()
//                .system("You are Java full Stack Course Assistant, Answer the questions dynamically")
//                .user(msg)
//                .call()
//                .content();

        return chatClient.prompt()
                .user(u -> u.text(template)
                        .param("studentName", studentName) //Passing the Dynamic values to the prompt
                        .param("query", query))
                .call()
                .content();
        //we can define system prompt here as well using .system() but we already have defined
        //While making the bean of the chatClient
    }
}

//In system we can give the system prompt. But it's tightly coupled.
//If we change the content of the prompt, we have to rebuild the application.
//So to solve this issue we define the PromptTempplates in resources/templates
//The extension for this is .st, The String template

//Prompt Templates Separate the prompt (text) from Business (Java) logic