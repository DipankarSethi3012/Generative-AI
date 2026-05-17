package com.ai.conversational.service;


import org.springframework.ai.chat.memory.ChatMemory; //That's an interface of Spring AI
//Spring AI Expects add(), get(), clear() methods as we are making our custom implementation so we have to override it

//Below import contains all the types of Messages (UserMessage, AssistantMessage, Message, MessageType)
import org.springframework.ai.chat.messages.*;

//The below import makes the database access simple. Without this we have to write a lot of boilerplate code
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

//This is the custom ChatMemory system that stores AI conversation into the postgres backend and retrieves from there as well
//Basically it's the backend for our AI Application
//High Level Flow: User Request -> Spring AI -> CustomPostgresChatMemory -> Postgres Database
//Next Message Flow: User Request -> Old Chat History -> LLM Context -> Better AI Response
@Service
public class CustomPostgresChatMemory implements ChatMemory {
    //By Implementing the ChatMemory we are following the Spring AI Memory Contract

    private final JdbcTemplate jdbcTemplate;

    public CustomPostgresChatMemory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Purpose of this is to save messages in the database
    //Two Parameters first is conversationID, second is list of messages
    @Override
    public void add(String conversationId, List<Message> messages) {
        //Hardcoded for now, In production gets from JWT/Security Context
        String userId = "user-123";
        String sql ="INSERT INTO ai_chat_history (user_id, conversation_id, message_type, content) VALUES (?, ?, ?, ?)";

        for (Message msg : messages) {

            // 1. Bulletproof Type Extraction (No dependency on getting correct Enum methods)
            String typeString = "SYSTEM";
            if (msg instanceof UserMessage) {
                typeString = "USER";
            } else if (msg instanceof AssistantMessage) {
                typeString = "ASSISTANT";
            }

            // 2. Safe Content Extraction (Handles nulls and unexpected objects gracefully)
            String contentString = msg.getText() != null ? msg.getText() : "";

            // 3. Passing strictly explicitly defined Strings to JDBC
            jdbcTemplate.update(sql,
                    userId,
                    conversationId,
                    typeString,
                    contentString
            );
        }
        //MessageType Contains the Role (User, Assistant, Tool)
    }

    //fetches the old chat history and converts it into a Spring AI memory object
    //Basically It loads the conversation Memory
    //Hig-Level-Flow = PostgresSQL -> Fetches the chat rows -> Convert rows -> Message Objects -> Returns to LLM -> LLM gets the conversation Context
    @Override
    public List<Message> get(String conversationId) {
        //Takes a conversation id, gives a chat history of that conversationId.
        //List<Messages> because it has multiple messages (user and AI responses)
        String sql = "SELECT message_type, content FROM ai_chat_history " +
                "WHERE conversation_id = ? " +
                "ORDER BY created_at DESC LIMIT 100";
        //We define the limit because LLM's have context window limits
        //Too many messages: Expensive, Slow, Token Overflow

        //execute the query and store messages in a list
        List<Message> history = jdbcTemplate.query(sql, (rs, rowNUm) -> {
            //It's a lambda expression that runs for every row
            //ResultSet rs -> Current Database Row
            // int rowNUm -> The current rowNum
            String messageType = rs.getString("message_type"); //checking message type if it's user or Assistant
            String content = rs.getString("content"); //Extracting the content

            if(MessageType.USER.getValue().equals(messageType)) {
                return new UserMessage(content);
            } else {
                return new AssistantMessage(content);
            }

        }, conversationId);

        java.util.Collections.reverse(history);
        return history;
    }

    @Override
    public void clear(String conversationId) {
        String sql ="DELETE FROM conversational_ai WHERE conversation_id = ?";
        jdbcTemplate.update(sql, conversationId);
    }
}

