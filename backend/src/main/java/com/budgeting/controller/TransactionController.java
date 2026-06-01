package com.budgeting.controller;

import java.io.File;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.budgeting.application.ListTransactionsCategoryByUseCase;
import com.budgeting.application.PersistTransactionUseCase;
import com.budgeting.application.input.TransactionInput;
import com.budgeting.domain.Category;
import com.budgeting.llm.LLMParser;
import com.budgeting.model.PiperSpeechModel;
import com.budgeting.service.TransactionCommandService;
import com.budgeting.service.WhisperService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ListTransactionsCategoryByUseCase listTransactionsCategoryByUseCase;
    private final WhisperService whisperService;
    private final LLMParser llmparser;
    private final TransactionCommandService transactionCommandService;
    private final PiperSpeechModel piperSpeechModel;
    
    public TransactionController(
            PersistTransactionUseCase persistTransactionUseCase,
            ListTransactionsCategoryByUseCase listTransactionsCategoryByUseCase,
            WhisperService whisperService,
            LLMParser llmparser,
            TransactionCommandService transactionCommandService,
            PiperSpeechModel piperSpeechModel) {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionsCategoryByUseCase = listTransactionsCategoryByUseCase;
        this.whisperService = whisperService;
        this.llmparser = llmparser;
        this.transactionCommandService = transactionCommandService;
        this.piperSpeechModel = piperSpeechModel;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse create(@RequestBody TransactionRequest request) {
        
    	var transaction = persistTransactionUseCase.persistTransaction(request.toInput());
        return TransactionResponse.from(transaction);
    }

    @GetMapping("/{category}")
    public List<TransactionResponse> readTransactions(@PathVariable Category category) {
        return listTransactionsCategoryByUseCase.listTransactionsByCategory(category)
                .stream()
                .map(TransactionResponse::from)
                .toList();
    }

    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "audio/mpeg")
    public ResponseEntity<ByteArrayResource> transcribe(@RequestParam("file") MultipartFile file) throws Exception {

        File temp = File.createTempFile("audio-", ".ogg");
        file.transferTo(temp);

        try {
            String text = whisperService.transcribe(temp.getAbsolutePath());
            TransactionInput input = llmparser.parse(text);
            var result = transactionCommandService.process(input);
            String speechText = String.format(
            	    "Transação registrada: %s, no valor de R$ %.2f, categoria %s.",
            	    result.description(),
            	    result.amount(),
            	    result.category()
        	);
            
            byte[] audio = piperSpeechModel.synthesize(speechText);
            
            var resource = new ByteArrayResource(audio);
    		
    		return ResponseEntity.ok()
    				.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, 
    						ContentDisposition.attachment()
    						.filename("audio.mp3")
    						.build()
    						.toString())
    				.body(resource);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } finally {
            temp.delete();
        }
    }
}