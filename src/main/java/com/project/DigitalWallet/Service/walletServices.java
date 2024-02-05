package com.project.DigitalWallet.Service;

import com.project.DigitalWallet.Config.MoneyConfig;
import com.project.DigitalWallet.DTO.DTO2;
import com.project.DigitalWallet.DTO.UserDTO;
import com.project.DigitalWallet.ErrorResponse;
import com.project.DigitalWallet.Model.Transaction;
import com.project.DigitalWallet.Model.Users;
import com.project.DigitalWallet.repo.transactionRepository;
import com.project.DigitalWallet.repo.walletRepo2;
import com.project.DigitalWallet.repo.walletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public  class walletServices implements walletRepo2 {
    @Autowired
    walletRepository wallet;
    @Autowired
    transactionRepository t_rep;
    @Autowired
    private MoneyConfig moneyConfig;


    @Override
    public ResponseEntity<UserDTO> createWallet(Users u) {
        if(wallet.findByUsername(u.getUsername())!=null){

            return new ResponseEntity<>(HttpStatus.CONFLICT);

//            return ResponseEntity<>.status(HttpStatus.)
        }
        try {
            Users user = wallet.save(u);
            UserDTO userDTO = new UserDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getDateTime() {
        LocalDateTime datetime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return datetime.format(formatter);
    }

    @Override
    public void saveTransaction(Long userId, String type, double amount, String formattedTimeStamp) {
        Transaction t = new Transaction();
        t.setUserId(userId);
        t.setTransactionType(type);
        t.setAmount(amount);
        t.setTimestamp(formattedTimeStamp);
        t_rep.save(t);
    }

    @Override
    public ResponseEntity<?> addMoney(String username, Users u) {

        try {
            Optional<Users> userdata = Optional.ofNullable(wallet.findByUsername(username));
            if (userdata.isPresent()) {
                if (u.getBalance() >= moneyConfig.getMinLoadAmount() && u.getBalance() <= moneyConfig.getMaxLoadAmount()) {
                    Users currentUsersData = userdata.get();
                    double currentBalance = currentUsersData.getBalance();

                    currentUsersData.setBalance(currentBalance + u.getBalance());

                    Users updatedUsersData = wallet.save(currentUsersData);
                    DTO2 dto2 = new DTO2(updatedUsersData);


                    return new ResponseEntity<>(dto2, HttpStatus.CREATED);
                }
                else {

                    String errorMessage = "Amount to be loaded should be between "
                            + moneyConfig.getMinLoadAmount() + " and " + moneyConfig.getMaxLoadAmount();
                    return new ResponseEntity<>(new ErrorResponse(errorMessage), HttpStatus.BAD_REQUEST);
                }
            }

            return new ResponseEntity<>(new ErrorResponse("User not found with the given username"), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<UserDTO> transferMoney(String username, Long userId2, Users u) {

        try {
            Optional<Users> user1Data = Optional.ofNullable(wallet.findByUsername(username));
            Optional<Users> user2Data = wallet.findById(userId2);
            if (user1Data.isPresent() && user2Data.isPresent()) {

                Users currentUsers1Data = user1Data.get();
                Users currentUsers2Data = user2Data.get();
                double currentBalance1 = currentUsers1Data.getBalance();
                if (currentBalance1 >= u.getBalance()) {
                    double currentBalance2 = currentUsers2Data.getBalance();
                    currentUsers1Data.setBalance(currentBalance1 - u.getBalance());
                    currentUsers2Data.setBalance(currentBalance2 + u.getBalance());
                    Users updatedUsers1Data = wallet.save(currentUsers1Data);
                    UserDTO dto = new UserDTO(updatedUsers1Data);

//                    Users updatedUsers2Data = wallet.save(currentUsers2Data);
//                    List<Users> updatedUsers = Arrays.asList(updatedUsers1Data, updatedUsers2Data);

                    return new ResponseEntity<>(dto, HttpStatus.CREATED);
                }

                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Override
    public ResponseEntity<String> checkBalance(Long userId){
        Optional<Users> user1Data = wallet.findById(userId);
        if (user1Data.isPresent()) {
            Users usersData = user1Data.get();
            double balance = usersData.getBalance();
            return  ResponseEntity.status(HttpStatus.OK).body("Wallet Amount : " + balance);
        }
        return ResponseEntity.status(HttpStatus.OK).body("No User Found");
    }


    @Override
    public ResponseEntity<List<Transaction>> getHistory(Long userId){
        List<Transaction> history= t_rep.findByuserIdOrderByTimestampDesc(userId);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @Override
    public String removeUser(Long userId){
        Optional<Users> user = wallet.findById(userId);
        if (user.isPresent()) {
//            Users removedUser = user.get();
//            List<Transaction> userTransactions = removedUser.getTransactions();
//            if (userTransactions != null) {
//                t_rep.deleteAll(userTransactions);
//            }
            wallet.deleteById(userId);

            return "Wallet of user with Users Id: "+userId+" is Deleted";
        }
        return "No user Found";
    }



        @Override
        public List<Transaction> filterTransactions(Long userId, String transactionType) {

            return t_rep.findByuserIdAndTransactionTypeOrderByTimestampDesc(userId,transactionType);
        }




    @Autowired

    private JavaMailSender javaMailSender;
    public void sendTransactionNotification(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

}

