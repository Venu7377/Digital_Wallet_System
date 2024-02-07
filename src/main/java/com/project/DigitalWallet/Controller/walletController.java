package com.project.DigitalWallet.Controller;

import com.project.DigitalWallet.Model.Users;
import com.project.DigitalWallet.Service.walletServices;
import com.project.DigitalWallet.TransferRequest;
import com.project.DigitalWallet.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("digitalWalletSystem/v1")

@RestController
public class walletController {

    @Autowired
    walletServices walletService;


    @PostMapping("/user/createWallet")
   public ResponseEntity<?> addUser(@RequestBody Users u) {
        return walletService.createWallet(u);
   }


@PostMapping ("/user/loadMoney")
public ResponseEntity<?> addMoney(@RequestBody Users u) {
       return walletService.addMoney(u.getUserId(), u.getBalance());
    }



    @PostMapping("/user/transferMoney")
    public ResponseEntity<?> transferMoney(@RequestBody TransferRequest t) {
        Long userId1 = t.getFromUserId();
        Long userId2 = t.getToUserId();
        return walletService.transferMoney(userId1, userId2, t.getBalance());
    }


    @GetMapping("/user/fetchBalance")
    public ResponseEntity<?> checkBalance(@RequestParam Long userId) {
                return walletService.checkBalance(userId);
    }


    @GetMapping("/user/fetchHistory")
    public ResponseEntity<?> getHistory(@RequestParam Long userId){
                return walletService.getHistory(userId);
    }



    @GetMapping("/user/transactions/filter")
    public ResponseEntity<?> filterTransactions( @RequestParam Long userId, @RequestParam String transactionType)
    {
        return walletService.filterTransactions(userId,transactionType);

    }


    @DeleteMapping("/user/removeUser")
    public  ResponseEntity<?> removeUser(@RequestParam Long userId){

        return walletService.removeUser(userId);
    }

    @GetMapping("/admin/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        List<UserInfo> allUsers = walletService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }


}
