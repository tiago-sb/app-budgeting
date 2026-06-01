package com.budgeting.model;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PiperSpeechModel {

    @Value("${piper.executable}")
    private String piperExecutable;

    @Value("${piper.model}")
    private String piperModel;

    public byte[] synthesize(String text) throws IOException, InterruptedException {

        Path output = Files.createTempFile("speech-", ".wav");

        ProcessBuilder pb = new ProcessBuilder(
            piperExecutable,
            "--model", piperModel,
            "--output_file", output.toString()
        );

        Process process = pb.start();

        try (OutputStream os = process.getOutputStream()) {
            os.write(text.getBytes(StandardCharsets.UTF_8));
        }

        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("Erro ao executar Piper");
        }

        return Files.readAllBytes(output);
    }
}