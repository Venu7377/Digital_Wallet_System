package com.project.DigitalWallet.repo;
import com.project.DigitalWallet.Model.Users;
import com.project.DigitalWallet.TransferRequest;
import org.springframework.http.ResponseEntity;

public interface walletRepository2 {
    ResponseEntity<?> createWallet(Users u);

    void saveTransaction(Long userId,String type,double amount,String formattedTimeStamp);

    ResponseEntity<?> addMoney(Users u,String password);
    ResponseEntity<?> transferMoney(TransferRequest transferRequest,String password);
     ResponseEntity<?> checkAmount(Long userId,String password);
     ResponseEntity<?> getHistory(Long userId,String password);
     ResponseEntity<?> removeUser(Long userId,String password);

    ResponseEntity<?> filterTransactions(Long userId, String transactionType,String password);
}
