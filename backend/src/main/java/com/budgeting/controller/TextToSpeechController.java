package com.budgeting.controller;

import java.io.IOException;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budgeting.model.PiperSpeechModel;


@RestController
@RequestMapping("/api")
public class TextToSpeechController {
	@Autowired
    private ChatClient chatClient;
	
	private final PiperSpeechModel piperSpeechModel;
	
	public TextToSpeechController(PiperSpeechModel piperSpeechModel) {
		this.piperSpeechModel = piperSpeechModel;
	}
	
	@PostMapping(value  = "/sinthesize", produces = "audio/mp3")
	public ResponseEntity<ByteArrayResource> sinthesize(@RequestBody SynthesizeRequest request) throws IOException, InterruptedException {
		var prompt = chatClient.prompt()
			.user(request.text())
			.call()
			.content();
		
		var audio = piperSpeechModel.synthesize(prompt);
		var resource = new ByteArrayResource(audio);
		
		return ResponseEntity.ok()
				.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, 
						ContentDisposition.attachment()
						.filename("audio.mp3")
						.build()
						.toString())
				.body(resource);
				
	}
	
	record SynthesizeRequest(String text) {
	}
}
