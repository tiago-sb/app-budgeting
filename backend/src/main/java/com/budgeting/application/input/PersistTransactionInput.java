package com.budgeting.application.input;

import org.springframework.ai.tool.annotation.ToolParam;

import com.budgeting.domain.Category;

public record PersistTransactionInput(
		@ToolParam(description = "Descrição de gasto") String description, 
		@ToolParam(description = "valor do gaasto") double amount, 
		@ToolParam(description = "categoria de uma transação") Category category) {
}
