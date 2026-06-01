package com.budgeting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.budgeting.application.output.TransactionOutput;
import com.budgeting.domain.Category;
import com.budgeting.repository.TransactionRepository;

@Service
public class TransactionQueryService {

    private final TransactionRepository repository;

    public TransactionQueryService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<TransactionOutput> listByCategory(Category category) {
        return repository.findAllByCategory(category)
                .stream()
                .map(TransactionOutput::from)
                .toList();
    }

    public List<TransactionOutput> listAll() {
        return repository.findAll()
                .stream()
                .map(TransactionOutput::from)
                .toList();
    }
}