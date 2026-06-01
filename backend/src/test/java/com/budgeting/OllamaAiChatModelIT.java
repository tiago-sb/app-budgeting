package com.budgeting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OllamaAiChatModelIT {

    @Autowired
    private ChatClient.Builder builder;

    @Test
    void should_receive_response() {
        var chatClient = builder.build();

        String response = chatClient.prompt()
                .user("Gere um registro de budgeting com descrição do gasto, valor em reais e local")
                .call()
                .content();
        
        assertThat(response).isNotEmpty();
        System.out.println(response);
    }
}