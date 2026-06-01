package com.budgeting.controller;

import com.budgeting.application.output.TransactionOutput;
import com.budgeting.domain.Category;

public record TransactionResponse(String id, String category, String description, double amount) {
	public TransactionResponse(
            String id,
            Category category,
            String description,
            double amount) {
        this(
            id,
            category.name(),
            description,
            amount
        );
    }

	public static TransactionResponse from(TransactionOutput output) {
		return new TransactionResponse(
				output.id(),
				output.category(),
				output.description(),
				output.amount());
	}
}
