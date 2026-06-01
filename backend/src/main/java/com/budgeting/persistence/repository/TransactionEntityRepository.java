package com.budgeting.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.budgeting.domain.Category;
import com.budgeting.persistence.entity.TransactionEntity;

public interface TransactionEntityRepository extends CrudRepository<TransactionEntity, UUID>{
	List<TransactionEntity> findAllByCategory(Category category);
}
