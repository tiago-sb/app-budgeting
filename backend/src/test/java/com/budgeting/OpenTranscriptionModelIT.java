package com.budgeting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.budgeting.service.WhisperService;

@SpringBootTest
public class OpenTranscriptionModelIT {

    @Autowired
    private WhisperService whisperService;
    
    @Autowired
    private OllamaChatModel ollamaChatModel;
    
    @Test
    void should_transcribe_audio() throws Exception {
    	String transcription = 
    			whisperService.
    			transcribe("src/test/java/com/budgeting/resources/audio/01.ogg");
    	
    	System.out.println(transcription);
    	
    	var chatClient = ChatClient.builder(ollamaChatModel)
                .defaultSystem("""
                        Você é um assistente financeiro.
                        Identifique gastos no texto informado.
                        """)
                .build();
    	
    	 String response = chatClient.prompt("""
                 Analise a seguinte transcrição:

                 %s
                 """.formatted(transcription))
                 .call()
                 .content();

    	 System.out.println(response);
        
    	assertThat(transcription).isNotBlank();
        assertThat(transcription).contains("padaria");
    }
}