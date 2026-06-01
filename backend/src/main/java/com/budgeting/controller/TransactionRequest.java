package com.budgeting.controller;

import com.budgeting.application.input.PersistTransactionInput;
import com.budgeting.domain.Category;

public record TransactionRequest(
		String description,
		Category category,
		double amount) {
	
	public PersistTransactionInput toInput() {
		 return new PersistTransactionInput(
	                description,
	                amount,
	                category);
	}
}
