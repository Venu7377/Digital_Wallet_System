package com.project.DigitalWallet.repo;

import com.project.DigitalWallet.Model.TransactionLimit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface transactionLimitRepository extends JpaRepository<TransactionLimit,Long> {

    TransactionLimit findByuserId(Long userId);
}
