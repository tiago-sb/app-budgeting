package com.budgeting.repository;

import java.util.List;
import com.budgeting.domain.Category;
import com.budgeting.domain.Transaction;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findAll();
    List<Transaction> findAllByCategory(Category category);
}