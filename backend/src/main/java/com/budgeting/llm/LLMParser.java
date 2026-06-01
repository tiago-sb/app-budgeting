package com.budgeting.llm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.budgeting.application.input.TransactionInput;

@Service
public class LLMParser {
    private final ChatClient chatClient;

    public LLMParser(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public TransactionInput parse(String text) {
        return chatClient.prompt()
    		.system("""
    			    Você é um parser de transações financeiras.
    			    Converta o texto do usuário em JSON com:
    			    - intent (CREATE_TRANSACTION)
    			    - description (string)
    			    - amount (número SEMPRE positivo, ex: 15.0)
    			    - category (MERCADO, FARMACIA, AUTO)
    			    - confidence (0 a 1)
    			    Se não tiver certeza de algum campo, use null.
    			""")
            .user(text)
            .call()
            .entity(TransactionInput.class);
    }
}