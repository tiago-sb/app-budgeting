package com.budgeting.persistence.entity;

import java.util.Objects;
import java.util.UUID;

import com.budgeting.domain.Category;
import com.budgeting.domain.Transaction;
import com.budgeting.domain.TransactionId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TransactionEntity {
	
	@Id
	private UUID id;
	
	private String description;
	private double amount;
	private Category category;
	
	public TransactionEntity() {
		super();
	}

	public TransactionEntity(String id, String description, double amount, Category category) {
	    this.id = UUID.fromString(id);
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
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public UUID getId() {
		return id;
	}
	
	public static TransactionEntity from(Transaction transaction) {
		return new TransactionEntity(
				transaction.getId().uuid().toString(),
				transaction.getDescription(),
				transaction.getAmount(),
				transaction.getCategory());
	}
	
	public Transaction toDomain() {
		return new Transaction(
				new TransactionId(this.id),
				this.description,
				this.amount,
				this.category);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(amount, category, description, id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionEntity other = (TransactionEntity) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount) && category == other.category
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id);
	}
}
