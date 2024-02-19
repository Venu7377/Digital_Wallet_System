package com.project.DigitalWallet.repo;

import com.project.DigitalWallet.Model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface transactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByuserId(Long userId, Pageable page);
    List<Transaction> findByuserIdAndTransactionType(Long userId, String transactionType, Pageable page);

}
