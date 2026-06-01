package com.budgeting.persistence.repository;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Repository;

import com.budgeting.domain.Category;
import com.budgeting.domain.Transaction;
import com.budgeting.persistence.entity.TransactionEntity;
import com.budgeting.repository.TransactionRepository;

@Repository
public class JpaTransactionRepository implements TransactionRepository {
	private final TransactionEntityRepository transactionEntityRepository; 
	
	public JpaTransactionRepository(TransactionEntityRepository transactionEntityRepository) {
		this.transactionEntityRepository = transactionEntityRepository;
	}
	
	@Override
	public Transaction save(Transaction transaction) {
		var entity = TransactionEntity.from(transaction);
		return transactionEntityRepository.save(entity).toDomain();
	}

	@Override
	public List<Transaction> findAllByCategory(Category category) {
		return transactionEntityRepository.findAllByCategory(category)
				.stream()
				.map(TransactionEntity::toDomain)
				.toList();
		
	}
	
	@Override
	public List<Transaction> findAll() {
	    return StreamSupport.stream(
	            transactionEntityRepository.findAll().spliterator(),
	            false
	    )
	    .map(TransactionEntity::toDomain)
	    .toList();
	}
}
