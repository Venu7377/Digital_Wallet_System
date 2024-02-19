package com.project.DigitalWallet.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.List;
import java.util.Map;

@RequestMapping("digitalWalletSystem/v1")
@SecurityRequirement(name = "basicAuth")
@RestController
public class walletController {
    @Autowired
    walletServices walletService;
    @Autowired
    ExternalInteraction externalInteraction;

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


    @GetMapping("/user/transactions")
    public ResponseEntity<ApiResponse<List<Transaction>>> getTransactions(
            @RequestParam Long userId,
            @RequestParam(required = false) String filterBy,
            @RequestHeader("Password") String password,
            @RequestParam int pageNumber,
            @RequestParam int pageSize) {
        return walletService.getTransactions(userId,password,pageNumber,pageSize,filterBy);
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
    public ResponseEntity< ApiResponse<List<Map<String,Object>>>> makeApiCall() throws JsonProcessingException {
        return externalInteraction.makeApiCall();
    }
}

