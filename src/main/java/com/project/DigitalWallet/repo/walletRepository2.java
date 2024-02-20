package com.project.DigitalWallet.repo;
import com.project.DigitalWallet.ApiResponse;
import com.project.DigitalWallet.DTO.UserDTO;
import com.project.DigitalWallet.DTO.WalletCreationResponse;
import com.project.DigitalWallet.Model.Transaction;
import com.project.DigitalWallet.Model.Users;
import com.project.DigitalWallet.TransferRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface walletRepository2 {
    ResponseEntity<ApiResponse<WalletCreationResponse>> createWallet(Users u);
    void saveTransaction(Long userId,String type,double amount,String formattedTimeStamp);
    ResponseEntity<ApiResponse<UserDTO>> addMoney(Users u, String password);
    ResponseEntity<ApiResponse<UserDTO>> transferMoney(TransferRequest transferRequest,String password);
    ResponseEntity<ApiResponse<String>> checkAmount(Long userId,String password);
    ResponseEntity<ApiResponse<List<Transaction>>> getHistory(Long userId, String password);
    ResponseEntity<ApiResponse<String>> removeUser(Long userId,String password);
    ResponseEntity<ApiResponse<List<Transaction>>> filterTransactions(Long userId, String transactionType,String password);
}
