package com.budgeting.application;

import org.springframework.stereotype.Service;

import com.budgeting.application.input.PersistTransactionInput;
import com.budgeting.application.output.TransactionOutput;
import org.springframework.ai.tool.annotation.Tool;
import com.budgeting.domain.Transaction;
import com.budgeting.repository.TransactionRepository;

@Service
public class PersistTransactionUseCase {
	private final TransactionRepository transactionRepository;
	
	public PersistTransactionUseCase(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository; 
	}

	@Tool(description = "persiste uma nova transação financeira")
	public TransactionOutput persistTransaction(PersistTransactionInput input) {
		var transaction = transactionRepository.save(
				new Transaction(
						input.description(), 
						input.amount(), 
						input.category()));
		
		return TransactionOutput.from(transaction);
	}
}
