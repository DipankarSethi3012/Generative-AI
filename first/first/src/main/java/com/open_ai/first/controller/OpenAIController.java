package com.open_ai.first.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //class will handle RESTful Web requests
//return value is serialized into (Json or XML) and sent back in HTTP Response
@RequestMapping("/api")
public class OpenAIController {
    private final ChatClient chatClient; //loading-it from spring AI

    //Constructor Dependency Injection, Spring Automatically injects ChatClient.Builder()
    public OpenAIController(ChatClient.Builder chatClient){
        this.chatClient = chatClient.build();
        //Spring Injects the Builder here
        //We call .build() and make the final ChatClient
    }

    //Giving message to the llm model (gpt)
    @GetMapping("/chat")
    public String chatOpenAIModel(@RequestParam("msg") String msg) {
        return chatClient.prompt(msg).call().content();
        //Internally
        //It makes the request
        //Send it to the model
        //Parses the response
    }
    //default ChatCLient is that for which we have added the dependency
}

//ChatClient
//It's a high level wrapper that makes easy to talk with AI Models
//WE can't directly call the OpenAI API, Spring AI provides a clean interface for it.

//RealWorld Analogy:
//ChatModel is an Engine
//ChatClient is a Driver
//we tell the driver to ride the car, Driver internally uses engine

//ChatClient handles
//Prompt Formatting
//API Call
//Response Parsing
//Fluent APi (Chain Style)

//ChatModel
//It's an actual AI model (LLM) that generates the response
//Ex: OpenAI GPT, Azure OpenAI, Local Models (Ollama, etc.)

//Technically
//ChatClient -> ChatModel -> OpenAI API

//We can directly use the ChatModel as well.
//But the manual work is more
//We have to handle the formatting as our own

//ChatClient.Builder
//Helper Object that collects the configuration to make the ChatClient Object

//We can't make ChatCLient Directly
//We first take the builder, pu the settings in it, then call .build() t make the final object

//Flow
//Builder (does's All setup) -> build() -> ChatClient(ready to use)

//Spring Boot application.properties/yaml se configuration load karke ChatModel aur ChatClient.Builder ko autoconfigure karta hai
//We can additionally customize this using the Builder pattern
//By calling the .build() we create an instance of the ChatClient
//Properties -> Spring Config -> Builder ready -> build() -> CHatClient()


//Design Patterns Spring AI Uses
//Problem
//Different LLM Providers
    //OpenAI -> Different request response format
    //Gemini -> Different struvture
    //Claude -> Different API
//If it direcṭly integrates
//Tight Coupling, Vendor Lock-in, Code messy, Switching Nightmare

//Spring AI uses 2 patterns
//Adapter Pattern -> TO hide the APi differences
//Strategy Pattern -> To choose the model at runtime

//Adapter Pattern in Spring AI
//Adapter pattern converts one interface into another so that incompatible systems can work together
//Common Interface -> ChatModel
//Adapters -> OpenAiChatModel, GeminiChatModel, ClaudeChatModel
//Flow -> Our Code -> ChatClient -> ChatModel (interface) -> Adapter -> Actual API

//Strategy Pattern in Spring AI
//Strategy Pattern allows selecting an algorithm or behavior at runtime
//Which ChatModel to use

//Types of Strategy
//Static Strategy (By default)
//Decide when the app starts, not at the runtime
//Dynamic Strategy (developer Driven)
//public String chat(String provider, String msg) {
//    ChatModel model;
//
//    if (provider.equals("openai")) {
//        model = openAiModel;
//    } else {
//        model = geminiModel;
//    }
//
//    return model.call(msg);
//}
//Switches at the runtime, actual Strategy Pattern
//Flow:  user input -> Strategy Selection -> ChatModel Chosen -> Execution


//Complete Flow
//Strategy Pattern (Choose Model at runtime) -> ChatModel INterface -> Adapter Pattern (COnvert to provider specific APi's) -> OpenAi/Gemini/Claude