package com.rag.firstrag.controller;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final ChatClient chatClient;
    private final VectorStore  vectorStore;

    @Value("classpath:/promptTemplates/ragTemplate.st")
    Resource promptTemplate;

    public RagController(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    @GetMapping("/chat")
    public ResponseEntity<?> chat(@RequestParam String question) {
        //Step 1: Build searchRequest as vector store takes search request to perform SemanticSearch
        // This is a searchBuild Request
        SearchRequest searchRequest = SearchRequest.builder()
                .query(question) //The user search request
                .topK(4) //How many docs are needed
                .similarityThresholdAll() //The minimum similarity
//                .similarityThreshold(1.0)
//                .filterExpression(question) //Here we can put the meta data filtering
                .build();
        //Search Request doesn't search it just provides the configuration
        //Actual search is done by VectorStoreRetriever using similaritySearch()
        //This class is extend by vectorStore

        //Step 2: That's where actual Semantic Search is being performed
        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);

        //Step 3: Convert the retrieved documents into String
        String content = similarDocs.stream()
                //stream is a way in Java to process the Collection/List data in a sequential pipeline
                //With the help of stream we can perform operations on the data in chain without using manual loops
                .map(this::documentContent)
                //extracting the actual text from the document
                //The purpose of map function is to convert or transform every element
                //map takes element one by one from similarDocs list.
                //similarDocs has DocumentType Elements.
                //pass into the documentContent function. That's why documentContent function has parameter of type Document
                //this means current class (RagController). Call the documentContent() method in RagController class
                .filter(t -> t != null && !t.isBlank())
                //Take elements based on some condition
                //Take those Strings that are not null and not empty
                .collect(Collectors.joining(
                        System.lineSeparator()
                                + "----" +
                                System.lineSeparator()
                ));
        //collect is used to convert the processed data by stream to final result

        if(content.isBlank()) {
            return ResponseEntity.ok("Couldn't find relevant information");
        }

        String result = chatClient.prompt()
                .system(s -> s.text(promptTemplate)
                        .param("documents", content))
                .user(question)
                .call()
                .content();

        return ResponseEntity.ok(result);
    }

    private String documentContent(Document document)
    {
        String content = document.getFormattedContent(); //returns the structured formatted content of the Document
        //getText() gives only raw text.
        //LLM better understand the formatted content than raw content

//        Internally Kya Kar Sakta Hai
//         Spring AI ka Document internally: metadata content formatting
//        combine karke formatted representation bana sakta hai.
        if(content == null || content.isBlank())
        {
            content = document.getText();  //fallback we are using
        }

        return content;
    }

}
