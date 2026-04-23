package com.infra.second.grok;

import java.util.List;

public class GrokResponse {
    public List<Choice> choices;

    public static class Choice {
        public Message message;
    }

    public static class Message {
        public String content;
    }
}
