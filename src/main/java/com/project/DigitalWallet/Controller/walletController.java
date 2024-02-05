package com.project.DigitalWallet.Controller;

import com.project.DigitalWallet.DTO.DTO2;
import com.project.DigitalWallet.DTO.UserDTO;
import com.project.DigitalWallet.Model.Transaction;
import com.project.DigitalWallet.Model.Users;
import com.project.DigitalWallet.Service.walletServices;
import com.project.DigitalWallet.TransferRequest;
import com.project.DigitalWallet.repo.transactionRepository;
import com.project.DigitalWallet.repo.walletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("digitalWalletSystem/v1/wallet")

@RestController
public class walletController {
    @Autowired
    walletRepository wallet;
    @Autowired
    transactionRepository t_rep;
    @Autowired
    walletServices w;


    @PostMapping("/createWallet")
   public ResponseEntity<UserDTO> addUser(@RequestBody Users u) {
//        w.sendTransactionNotification("venugundam7378@gmail.com","Registration","Hi,You have successfully Registered");
        return w.createWallet(u);
   }


@PostMapping ("/loadMoney")
public ResponseEntity<DTO2> addMoney(@RequestBody Users u) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()) {

        String username = authentication.getName();
        if(username.equals(u.getUsername())){
            Long userId=wallet.findByUsername(u.getUsername()).getUserId();
            w.saveTransaction(userId,"Loaded",u.getBalance(),w.getDateTime());
            return w.addMoney(username,u);
        }

    }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

//        w.sendTransactionNotification("venugundam7378@gmail.com","Money Added Successfully","Rs"+u.getBalance()+" is added to your wallet  Successfully!! \n" +w.getDateTime());

}




    @PostMapping(value ="/transferMoney", consumes={"application/json"})
    public ResponseEntity<UserDTO> transferMoney(@RequestBody Users u) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            String username = authentication.getName();
//            Users u = transferRequest.getUsers();
            if (username.equals(u.getUsername())) {
                Long userId1=wallet.findByUsername(u.getUsername()).getUserId();
                Long userId2 = u.getUserId();
                w.saveTransaction(userId1,"Debited",u.getBalance(),w.getDateTime());
                w.saveTransaction(userId2,"Credited",u.getBalance(),w.getDateTime());
                return w.transferMoney(username, userId2, u);
            }
//        Long userId1 = transferRequest.getUserId1();

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @GetMapping("/fetchBalance")
    public ResponseEntity<String> checkBalance(@RequestParam Long userId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<Users> u=wallet.findById(userId);
            if(u.isPresent()) {
                String u_name = u.get().getUsername();
                if (username.equals(u_name)) {
                    return w.checkBalance(userId);
                }
            }


        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }





    @GetMapping("/fetchHistory")
    public ResponseEntity<List<Transaction>> getHistory(@RequestParam Long userId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<Users> u=wallet.findById(userId);
            if(u.isPresent()) {
                String u_name = u.get().getUsername();
                if (username.equals(u_name)) {
                    return w.getHistory(userId);
                }
            }

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        w.sendTransactionNotification("venugundam7378@gmail.com","Statement", String.valueOf(w.getHistory(u.getUserId())));

    }



    @GetMapping("/transactions/filter")
    public ResponseEntity<List<Transaction>> filterTransactions( @RequestParam Long userId, @RequestParam String transactionType)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<Users> u=wallet.findById(userId);
            if(u.isPresent()) {
                String u_name = u.get().getUsername();
                if (username.equals(u_name)) {
                    List<Transaction> filteredTransactions = w.filterTransactions(userId,transactionType);
                    return ResponseEntity.ok(filteredTransactions);
                }
            }

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }


    @DeleteMapping("/removeUser")
    public String removeUser(@RequestBody Users u){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<Users> user=wallet.findById(u.getUserId());
            if(user.isPresent()) {
                String u_name = user.get().getUsername();
                if (username.equals(u_name)) {

                    return w.removeUser(u.getUserId());
                }
            }

        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       return "No User Found";
    }

}
