package com.budgeting.application.input;

import com.budgeting.domain.Category;

public record TransactionInput(
	    String intent,
	    String description,
	    Double amount,
	    Category category,
	    Double confidence
	) {}