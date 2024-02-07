package com.project.DigitalWallet.repo;

import com.project.DigitalWallet.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface transactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByuserIdOrderByTimestampDesc(Long userId);
    List<Transaction> findByuserIdAndTransactionTypeOrderByTimestampDesc(Long userId,String transactionType);

}
