package com.project.DigitalWallet.repo;

import com.project.DigitalWallet.DTO.DTO2;
import com.project.DigitalWallet.DTO.UserDTO;
import com.project.DigitalWallet.Model.Transaction;
import com.project.DigitalWallet.Model.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface walletRepo2 {
    ResponseEntity<UserDTO> createWallet(Users u);
    String getDateTime();
    void saveTransaction(Long userId,String type,double amount,String formattedTimeStamp);

ResponseEntity<?> addMoney(Long userId,double amount);
    ResponseEntity<?> transferMoney(Long userId1, Long userId2, double amount);
     ResponseEntity<?> checkBalance(Long userId);
     ResponseEntity<?> getHistory(Long userId);
     ResponseEntity<?> removeUser(Long userId);

    ResponseEntity<?> filterTransactions(Long userId, String transactionType);
}
