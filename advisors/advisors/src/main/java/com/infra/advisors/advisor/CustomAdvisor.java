package com.infra.advisors.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;

public class CustomAdvisor implements BaseAdvisor {

    private static final String START_TIME_NANO = "CustomAdvisorStartTime";
    private static final String END_TIME_NANO = "CustomAdvisorEndTime";

    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        return chatClientRequest.mutate()
                .context(START_TIME_NANO, System.nanoTime())
                .build();
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        Object startTime = chatClientResponse.context().get(START_TIME_NANO);

        if(startTime instanceof Long startTimeNano) {
            long endTime = (System.nanoTime() - startTimeNano)/1_00_00;
            System.out.println(endTime);

            return chatClientResponse.mutate().context(END_TIME_NANO, endTime)
                    .build();
        }

        return chatClientResponse;
    }


    @Override
    public int getOrder() {
        return 1;
    }
}
