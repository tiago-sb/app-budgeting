package com.budgeting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToolCalling {

    @Autowired
    OllamaChatModel ollamaChatModel;

    static class MathTools {
        @Tool(description = "Soma dois números")
        public int sum(int a, int b) {
            return a + b;
        }

        @Tool(description = "Subtrai dois números")
        public int diff(int a, int b) {
        	System.out.println("DIFF EXECUTADA");
        	return a - b;
        }
    }

    @Test
    void should_execute_sum() {

        var chatClient = ChatClient.builder(ollamaChatModel)
                .defaultSystem("""
                    Você é um matemático.
                    Você DEVE usar ferramentas para fazer cálculos.
                    Nunca responda cálculos manualmente.
                """)
                .defaultTools(new MathTools())
                .build();

        String response = chatClient.prompt("""
                Use a ferramenta de soma para calcular:
                20 - 20
                """)
                .call()
                .content();

        System.out.println(response);

        assertThat(response).isNotEmpty();
    }
}