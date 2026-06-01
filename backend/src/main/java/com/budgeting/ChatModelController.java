package com.budgeting;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class ChatModelController {
	private final OllamaChatModel ollamaChatModel;

	public ChatModelController(OllamaChatModel ollamaChatModel) {
		this.ollamaChatModel = ollamaChatModel;
	}
	
	@GetMapping("/chat-model")
	String chat(String prompt) {
		return this.ollamaChatModel.call(prompt);
	}
}
