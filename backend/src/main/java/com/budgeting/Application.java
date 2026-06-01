package com.budgeting;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	@Bean
	ChatClient chatClient(ChatClient.Builder buider) {
		return buider.build();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
