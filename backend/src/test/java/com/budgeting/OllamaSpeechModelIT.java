package com.budgeting;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.budgeting.model.PiperSpeechModel;

@SpringBootTest
class OllamaSpeechModelIT {
	@Autowired
    private ChatClient chatClient;

	
	@Autowired
    private PiperSpeechModel piperSpeechModel;
	
    @Test
    void shouldProduceAudio() throws Exception {
        // Obtém resposta do Ollama
        String responseText = chatClient.prompt()
        		.user("O valor ficou 80 reais. Posso confirmar o pagamento?")
        		.call()
        		.content();

        // Gera áudio usando Piper
        byte[] audio = piperSpeechModel.synthesize(responseText);
        
        assertThat(responseText).isNotBlank();
        assertThat(audio).isNotNull();
        assertThat(audio.length).isGreaterThan(1024);

        Path tempFile = Files.createTempFile("AUDIO-", ".wav");
        Files.write(tempFile, audio);

        System.out.println("Resposta do Ollama: " + responseText);
        System.out.println("Arquivo gerado: " + tempFile.toAbsolutePath());
    }
}