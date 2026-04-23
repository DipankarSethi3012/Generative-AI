package com.infra.second.grok;

import java.util.List;

public class GrokRequest {
    public String model;
    public List<Message> messages;

    public static class Message {
        public String role;
        public String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
