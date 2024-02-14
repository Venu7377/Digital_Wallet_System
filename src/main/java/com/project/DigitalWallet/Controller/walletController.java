package com.project.DigitalWallet.Controller;

import com.project.DigitalWallet.ApiInteraction.ExternalInteraction;
import com.project.DigitalWallet.ApiResponse;
import com.project.DigitalWallet.DTO.UserDTO;
import com.project.DigitalWallet.DTO.UserDtoForAdmin;
import com.project.DigitalWallet.DTO.WalletCreationResponse;
import com.project.DigitalWallet.Model.Transaction;
import com.project.DigitalWallet.Model.Users;
import com.project.DigitalWallet.Service.walletServices;
import com.project.DigitalWallet.TransferRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("digitalWalletSystem/v1")
@SecurityRequirement(name = "basicAuth")
@RestController
public class walletController {
    @Autowired
    walletServices walletService;
    private final ExternalInteraction externalInteraction;

    public walletController(ExternalInteraction externalInteraction) {
        this.externalInteraction = externalInteraction;
    }

    @PostMapping("/user/createWallet")
   public ResponseEntity<ApiResponse<WalletCreationResponse>> addUser(@RequestBody @Valid Users u) {
        return walletService.createWallet(u);
   }


@PostMapping ("/user/loadMoney")
public ResponseEntity<ApiResponse<UserDTO>> addMoney(@RequestBody Users u, @RequestHeader("Password") String password) {
       return walletService.addMoney(u,password);
    }



    @PostMapping("/user/transferMoney")
    public ResponseEntity<ApiResponse<UserDTO>> transferMoney(@RequestBody TransferRequest t,@RequestHeader("Password") String password) {
        return walletService.transferMoney(t,password);
    }


    @GetMapping("/user/fetchBalance")
    public ResponseEntity<ApiResponse<String>> checkBalance(@RequestParam Long userId,@RequestHeader("Password") String password) {
                return walletService.checkAmount(userId,password);
    }


    @GetMapping("/user/fetchHistory")
    public ResponseEntity<ApiResponse<List<Transaction>>> getHistory(@RequestParam Long userId, @RequestHeader("Password") String password){
                return walletService.getHistory(userId,password);
    }


    @GetMapping("/user/transactions/filter")
    public ResponseEntity<ApiResponse<List<Transaction>>> filterTransactions( @RequestParam Long userId, @RequestParam String transactionType,@RequestHeader("Password") String password)
    {
        return walletService.filterTransactions(userId,transactionType,password);

    }


    @DeleteMapping("/user/removeUser")
    public  ResponseEntity<ApiResponse<String>> removeUser(@RequestParam Long userId,@RequestHeader("Password") String password){

        return walletService.removeUser(userId,password);
    }

    @GetMapping("/admin/getAllUsers")
    public
    ResponseEntity<ApiResponse<List<UserDtoForAdmin>>> getAllUsers() {
        return walletService.getAllUsers();
    }



    @GetMapping("/fetchExternalWallet")
    public String makeApiCall() {
        try {
            return externalInteraction.makeApiCall();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error making API call";
        }
    }
}

