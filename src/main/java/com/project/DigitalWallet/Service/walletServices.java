package com.project.DigitalWallet.Service;

import com.project.DigitalWallet.ApiResponse;
import com.project.DigitalWallet.DTO.UserDTO;
import com.project.DigitalWallet.DTO.UserDtoForAdmin;
import com.project.DigitalWallet.DTO.WalletCreationResponse;
import com.project.DigitalWallet.ExceptionHandlers.InvalidAmountException;
import com.project.DigitalWallet.ExceptionHandlers.InvalidPasswordException;
import com.project.DigitalWallet.ExceptionHandlers.UserNotFoundException;
import com.project.DigitalWallet.Model.PasswordEntity;
import com.project.DigitalWallet.Model.Transaction;
import com.project.DigitalWallet.Model.Users;
import com.project.DigitalWallet.PasswordGenerator.PasswordGenerator;
import com.project.DigitalWallet.PropertyReader;
import com.project.DigitalWallet.ResponseCode;
import com.project.DigitalWallet.TransferRequest;
import com.project.DigitalWallet.repo.PasswordRepository;
import com.project.DigitalWallet.repo.transactionRepository;
import com.project.DigitalWallet.repo.walletRepository;
import com.project.DigitalWallet.repo.walletRepository2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public  class walletServices implements walletRepository2 {
    @Autowired
    walletRepository wallet;
    @Autowired
    transactionRepository t_rep;

    @Autowired
    PropertyReader propertyReader;
    @Autowired
    PasswordGenerator passwordGenerator;
    @Autowired
    PasswordRepository passwordRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Logger logger = LogManager.getLogger(walletServices.class);

    @Override
    public ResponseEntity<ApiResponse<WalletCreationResponse>> createWallet(Users u) {

            Users user = wallet.save(u);
            String generatedPassword = passwordGenerator.generateUniquePassword();
            PasswordEntity passwordEntity = new PasswordEntity();
            passwordEntity.setUserId(u.getUserId());
            passwordEntity.setPassword(passwordEncoder.encode(generatedPassword));
            passwordRepository.save(passwordEntity);
            WalletCreationResponse response = new WalletCreationResponse(user, generatedPassword);
            logger.info("Wallet created successfully for user: {}", u.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDescription(), response));

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
    public ResponseEntity<ApiResponse<UserDTO>> addMoney(Users u, String password) {

        Users currentUserData = wallet.findById(u.getUserId())
                .orElseThrow(() -> new UserNotFoundException(u.getUserId()));
        if (passwordEncoder.matches(password, getPasswordByUserId(u.getUserId()))) {
            String min_money = propertyReader.getProperty("money.load.min");
            String max_money = propertyReader.getProperty("money.load.max");
            if (u.getAmount() < Double.parseDouble(min_money) || u.getAmount() > Double.parseDouble(max_money)) {
                throw new InvalidAmountException("Amount to be loaded should be between " + Double.parseDouble(min_money) + " and " + Double.parseDouble(max_money));
            } else {
                saveTransaction(u.getUserId(), "Loaded", u.getAmount(), getDateTime());
                double currentAmount = currentUserData.getAmount();
                currentUserData.setAmount(currentAmount + u.getAmount());
                Users updatedUsersData = wallet.save(currentUserData);
                UserDTO dto = new UserDTO(updatedUsersData);
                logger.info("Money added successfully to user's wallet: {}", u.getUserId());
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDescription(), dto));
            }
        }else {
            throw new InvalidPasswordException(u.getUserId());
        }
    }


    @Override
    public ResponseEntity<ApiResponse<UserDTO>> transferMoney(TransferRequest transferRequest, String password) {

        Users currentUsers1Data = wallet.findById(transferRequest.getFromUserId())
                .orElseThrow(() -> new UserNotFoundException(transferRequest.getFromUserId()));

        Users currentUsers2Data = wallet.findById(transferRequest.getToUserId())
                .orElseThrow(() -> new UserNotFoundException(transferRequest.getToUserId()));

        if (passwordEncoder.matches(password, getPasswordByUserId(transferRequest.getFromUserId()))) {

            double currentAmount1 = currentUsers1Data.getAmount();
            String max_transfer_money = propertyReader.getProperty("money.transfer.max");
            String min_transfer_money = propertyReader.getProperty("money.transfer.min");
            if (transferRequest.getAmount() <= Double.parseDouble(max_transfer_money)) {
                if(transferRequest.getAmount()>0){
                if(currentAmount1 >= transferRequest.getAmount()) {
                    double currentAmount2 = currentUsers2Data.getAmount();
                    currentUsers1Data.setAmount(currentAmount1 - transferRequest.getAmount());
                    currentUsers2Data.setAmount(currentAmount2 + transferRequest.getAmount());
                    Users updatedUsers1Data = wallet.save(currentUsers1Data);
                    UserDTO dto = new UserDTO(updatedUsers1Data);
                    saveTransaction(transferRequest.getFromUserId(), "Debited", transferRequest.getAmount(), getDateTime());
                    saveTransaction(transferRequest.getToUserId(), "Credited", transferRequest.getAmount(), getDateTime());
                    logger.info("Money transferred successfully from user {} to user {}: {}",
                            transferRequest.getFromUserId(), transferRequest.getToUserId(), transferRequest.getAmount());

                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDescription(), dto));
                }
                else {
                    saveTransaction(transferRequest.getFromUserId(), "Failed", transferRequest.getAmount(), getDateTime());
                    throw new InvalidAmountException("Insufficient Amount for transfer");
                }
                } else {
                    throw new InvalidAmountException("Minimum Amount that can be transferred is " + Double.parseDouble(min_transfer_money));
                }
            }
            else {
                throw new InvalidAmountException("Maximum Amount that can be transferred is" + Double.parseDouble(max_transfer_money));
            }
        }
         throw new InvalidPasswordException(transferRequest.getFromUserId());
    }



    @Override
    public ResponseEntity<ApiResponse<String>> checkAmount(Long userId, String password) {
            Users currentUserData = wallet.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            if (passwordEncoder.matches(password, getPasswordByUserId(userId))) {
                double amount = currentUserData.getAmount();
                logger.info("Checked wallet amount for user: {}", userId);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDescription(), "Your Wallet Amount : " + amount));
            }
            throw new InvalidPasswordException(userId);
    }

    @Override
    public ResponseEntity<ApiResponse<List<Transaction>>> getHistory(Long userId, String password) {

            Users currentUserData = wallet.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            if (passwordEncoder.matches(password, getPasswordByUserId(userId))) {
                List<Transaction> history = t_rep.findByuserIdOrderByTimestampDesc(userId);
                logger.info("Retrieved transaction history for user: {}", userId);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDescription(), history));
            }else {
                throw new InvalidPasswordException(userId);
            }

    }

    @Override
    public ResponseEntity<ApiResponse<String>> removeUser(Long userId, String password) {
            Users currentUserData = wallet.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            if (passwordEncoder.matches(password, getPasswordByUserId(userId))) {
                wallet.deleteById(userId);
                logger.info("Removed user with Users Id: {}", userId);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDescription(), "Wallet of user with Users Id: " + userId + " is Deleted"));
            }
            else {
                throw new InvalidPasswordException(userId);
            }
    }

    @Override
    public ResponseEntity<ApiResponse<List<Transaction>>> filterTransactions(Long userId, String transactionType, String password) {

            Users currentUserData = wallet.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            if (passwordEncoder.matches(password, getPasswordByUserId(userId))) {
                List<Transaction> filteredTransactions = t_rep.findByuserIdAndTransactionTypeOrderByTimestampDesc(userId, transactionType);
                logger.info("Filtered transactions for user: {}", userId);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDescription(), filteredTransactions));
            }else {
                throw new InvalidPasswordException(userId);
            }

    }

    public ResponseEntity<ApiResponse<List<UserDtoForAdmin>>> getAllUsers() {
            List<Users> allUsers = wallet.findAll();
            List<UserDtoForAdmin> userInfoList = new ArrayList<>();
            for (Users user : allUsers) {
                UserDtoForAdmin userInfo = new UserDtoForAdmin(user.getUserId(), user.getName());
                userInfoList.add(userInfo);
            }
            logger.info("Retrieved information for all users");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDescription(), userInfoList));

    }


    public String getPasswordByUserId(Long userId) {
            Optional<PasswordEntity> passwordEntity = passwordRepository.findById(userId);
            if (passwordEntity.isPresent()) {
                return passwordEntity.get().getPassword();
            } else {
                return null;
            }

    }
}



