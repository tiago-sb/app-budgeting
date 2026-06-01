package com.budgeting.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.budgeting.application.input.TransactionInput;
import com.budgeting.application.output.TransactionOutput;
import com.budgeting.domain.Transaction;
import com.budgeting.repository.TransactionRepository;

@Service
public class TransactionCommandService {

    private final TransactionRepository repository;
    private static final Logger log = LoggerFactory.getLogger(TransactionCommandService.class);

    public TransactionCommandService(TransactionRepository repository) {
        this.repository = repository;
    }

    public TransactionOutput process(TransactionInput input) {

        if (input.amount() == null || input.amount() <= 0) {
            throw new IllegalArgumentException(
                "Valor inválido retornado pela IA: " + input.amount());
        }

        if (input.category() == null) {
            throw new IllegalArgumentException(
                "Categoria inválida retornada pela IA");
        }

        if (input.description() == null || input.description().isBlank()) {
            throw new IllegalArgumentException(
                "Descrição inválida retornada pela IA");
        }

        if (input.confidence() != null && input.confidence() < 0.7) {
        	log.warn("Confiança baixa na transcrição: {}", input.confidence());
        }

        var transaction = repository.save(new Transaction(
            input.description(),
            Math.abs(input.amount()),
            input.category()
        ));

        return TransactionOutput.from(transaction);
    }
}