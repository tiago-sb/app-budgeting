package com.budgeting.controller;

import com.budgeting.service.WhisperService;

import java.io.File;

import javax.management.RuntimeErrorException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class TranscriptionController {
    private final WhisperService whisperService;
	
	public TranscriptionController(WhisperService whisperService) {
		this.whisperService = whisperService;
	}
	
	@PostMapping(value = "/transcribe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String transcribe(@RequestParam("file") MultipartFile file) throws Exception {
		try {
			File file_temp = File.createTempFile("audio-", ".ogg");
			file.transferTo(file_temp);
			
			String transcription = whisperService
					.transcribe(file_temp.getAbsolutePath());
			
			file_temp.delete();
			return transcription;
		} catch (Error e) {
			throw new RuntimeErrorException(e);
		}
	}
}
