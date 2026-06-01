package com.budgeting.application;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import com.budgeting.application.output.TransactionOutput;
import com.budgeting.domain.Category;
import com.budgeting.repository.TransactionRepository;

@Service
public class ListTransactionsCategoryByUseCase {
	private final TransactionRepository transactionRepository;
	
	public ListTransactionsCategoryByUseCase(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	@Tool(description = "Lista transações financeiras por categoria")
	public List<TransactionOutput> listTransactionsByCategory(@ToolParam(description = "Categoria de uma transação") Category category) {
		return transactionRepository.findAllByCategory(category)
				.stream()
				.map(TransactionOutput::from)
				.toList();
	}
}
