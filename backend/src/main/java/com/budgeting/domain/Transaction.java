package com.budgeting.domain;

public class Transaction {
	private TransactionId id;
	private String description;
	private double amount;
	private Category category;
	
	public Transaction(String description, double amount, Category category) {
		this.id = new TransactionId();
		this.description = description;
		this.amount = amount;
		this.category = category;
	}

	public Transaction(TransactionId transactionId, String description, double amount, Category category) {
		this.id = transactionId;
		this.description = description;
		this.amount = amount;
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public TransactionId getId() {
		return id;
	}
}
