package com.project.DigitalWallet.Service;

import com.project.DigitalWallet.ExceptionHandlers.InvalidAmountException;
import com.project.DigitalWallet.ExceptionHandlers.UserNotFoundException;
import com.project.DigitalWallet.Model.Transaction;
import com.project.DigitalWallet.Model.Users;
import com.project.DigitalWallet.Response;
import com.project.DigitalWallet.UserInfo;
import com.project.DigitalWallet.repo.transactionRepository;
import com.project.DigitalWallet.repo.walletRepository2;
import com.project.DigitalWallet.repo.walletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public  class walletServices implements walletRepository2 {
    @Autowired
    walletRepository wallet;
    @Autowired
    transactionRepository t_rep;

    @Autowired
    Environment env;


    @Override
    public ResponseEntity<?> createWallet(Users u) {
        try {
            Users user = wallet.save(u);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public static String getDateTime() {
        LocalDateTime datetime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return datetime.format(formatter);
    }

    @Override
    public void saveTransaction(Long userId, String type, double amount, String formattedTimeStamp) {
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setTimestamp(formattedTimeStamp);
        t_rep.save(transaction);
    }

    @Override
    public ResponseEntity<?> addMoney(Long userId,double amount) {

        Users currentUserData = wallet.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        if (amount < Double.parseDouble(env.getProperty("money.load.min")) || amount > Double.parseDouble(env.getProperty("money.load.max"))) {
            throw new InvalidAmountException("Amount to be loaded should be between " +Double.parseDouble(env.getProperty("money.load.min")) + " and " + Double.parseDouble(env.getProperty("money.load.max")));
        }
        else {
            saveTransaction(userId, "Loaded", amount, getDateTime());

        }
        double currentBalance = currentUserData.getBalance();
        currentUserData.setBalance(currentBalance + amount);
        Users updatedUsersData = wallet.save(currentUserData);
        return new ResponseEntity<>(updatedUsersData, HttpStatus.CREATED);

    }


    @Override
    public ResponseEntity<?> transferMoney(Long userId1, Long userId2, double amount) {

        Users currentUsers1Data = wallet.findById(userId1)
                .orElseThrow(() -> new UserNotFoundException(userId1));

        Users currentUsers2Data = wallet.findById(userId2)
                .orElseThrow(() -> new UserNotFoundException(userId2));

        double currentBalance1 = currentUsers1Data.getBalance();

        if (amount <= Double.parseDouble(env.getProperty("money.transfer.max"))) {
            if (currentBalance1 >= amount) {
                double currentBalance2 = currentUsers2Data.getBalance();
                currentUsers1Data.setBalance(currentBalance1 - amount);
                currentUsers2Data.setBalance(currentBalance2 + amount);
                Users updatedUsers1Data = wallet.save(currentUsers1Data);
                saveTransaction(userId1, "Debited", amount, getDateTime());
                saveTransaction(userId2, "Credited", amount, getDateTime());

                return new ResponseEntity<>(updatedUsers1Data, HttpStatus.CREATED);
            } else {
                throw new InvalidAmountException("Insufficient balance for transfer");
            }
        } else {
            throw new InvalidAmountException("Maximum Amount that can be transferred is"+Double.parseDouble(env.getProperty("money.transfer.max")));
        }

    }



    @Override
    public ResponseEntity<?> checkBalance(Long userId){
        Users currentUserData = wallet.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        double balance = currentUserData.getBalance();
        return  ResponseEntity.status(HttpStatus.OK).body(new Response("Your Wallet Amount : " + balance));
    }



    @Override
    public ResponseEntity<?> getHistory(Long userId){
        Users currentUserData = wallet.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        List<Transaction> history= t_rep.findByuserIdOrderByTimestampDesc(userId);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeUser(Long userId){
        Users currentUserData = wallet.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        wallet.deleteById(userId);
        return new ResponseEntity<>(new Response("Wallet of user with Users Id: "+userId+" is Deleted"),HttpStatus.OK);

    }


    @Override
    public ResponseEntity<?> filterTransactions(Long userId, String transactionType) {
        Users currentUserData = wallet.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return new ResponseEntity<>(t_rep.findByuserIdAndTransactionTypeOrderByTimestampDesc(userId,transactionType),HttpStatus.OK);
    }

    public List<UserInfo> getAllUsers() {
        List<Users> allUsers = wallet.findAll();
        List<UserInfo> userInfoList = new ArrayList<>();

        for (Users user : allUsers) {
            UserInfo userInfo = new UserInfo(user.getUserId(), user.getName());
            userInfoList.add(userInfo);
        }

        return userInfoList;
    }


}

