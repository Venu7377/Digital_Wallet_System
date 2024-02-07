package com.project.DigitalWallet.repo;
import com.project.DigitalWallet.Model.Users;
import org.springframework.http.ResponseEntity;

public interface walletRepository2 {
    ResponseEntity<?> createWallet(Users u);

    void saveTransaction(Long userId,String type,double amount,String formattedTimeStamp);

ResponseEntity<?> addMoney(Long userId,double amount);
    ResponseEntity<?> transferMoney(Long userId1, Long userId2, double amount);
     ResponseEntity<?> checkBalance(Long userId);
     ResponseEntity<?> getHistory(Long userId);
     ResponseEntity<?> removeUser(Long userId);

    ResponseEntity<?> filterTransactions(Long userId, String transactionType);
}
