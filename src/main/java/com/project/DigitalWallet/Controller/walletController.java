package com.project.DigitalWallet.Controller;

import com.project.DigitalWallet.Model.Users;
import com.project.DigitalWallet.Service.walletServices;
import com.project.DigitalWallet.TransferRequest;
import com.project.DigitalWallet.DTO.UserDtoForAdmin;
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
public ResponseEntity<?> addMoney(@RequestBody Users u,@RequestHeader("Password") String password) {
       return walletService.addMoney(u,password);
    }



    @PostMapping("/user/transferMoney")
    public ResponseEntity<?> transferMoney(@RequestBody TransferRequest t,@RequestHeader("Password") String password) {
        return walletService.transferMoney(t,password);
    }


    @GetMapping("/user/fetchBalance")
    public ResponseEntity<?> checkBalance(@RequestParam Long userId,@RequestHeader("Password") String password) {
                return walletService.checkAmount(userId,password);
    }


    @GetMapping("/user/fetchHistory")
    public ResponseEntity<?> getHistory(@RequestParam Long userId,@RequestHeader("Password") String password){
                return walletService.getHistory(userId,password);
    }


    @GetMapping("/user/transactions/filter")
    public ResponseEntity<?> filterTransactions( @RequestParam Long userId, @RequestParam String transactionType,@RequestHeader("Password") String password)
    {
        return walletService.filterTransactions(userId,transactionType,password);

    }


    @DeleteMapping("/user/removeUser")
    public  ResponseEntity<?> removeUser(@RequestParam Long userId,@RequestHeader("Password") String password){

        return walletService.removeUser(userId,password);
    }

    @GetMapping("/admin/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        List<UserDtoForAdmin> allUsers = walletService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }


}
