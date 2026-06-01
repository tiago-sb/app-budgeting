package com.budgeting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OllamaAiChatClientIT {
	@Autowired
	OllamaChatModel ollamaChatModel;
	
	@Test
	void shoud_execute_sum() {
		var chatClient = ChatClient.builder(ollamaChatModel)
		.defaultSystem("Você é um matemático")
		.build();
		
		var response = chatClient.prompt("Some")
		.call()
		.content();
		
		assertThat(response).isNotEmpty();
		System.out.println(response);
	}
}
