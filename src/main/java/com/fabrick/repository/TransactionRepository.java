package com.fabrick.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fabrick.data.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

	@Query(value = "SELECT e.* FROM transactions e WHERE accounting_date >=:from and accounting_date <=:to and account_id =:accountId", nativeQuery = true)
	List<Transaction> findTransactions(LocalDate from, LocalDate to, Long accountId);

}
