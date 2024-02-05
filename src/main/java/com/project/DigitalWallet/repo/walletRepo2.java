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
//     ResponseEntity<Users> addMoney(Long userId, Users u);
ResponseEntity<?> addMoney(String username, Users u);
    ResponseEntity<UserDTO> transferMoney(String username, Long userId2, Users u);
     ResponseEntity<String> checkBalance(Long userId);
     ResponseEntity<List<Transaction>> getHistory(Long userId);
     String removeUser(Long userId);



    List<Transaction> filterTransactions(Long userId, String transactionType);
}
