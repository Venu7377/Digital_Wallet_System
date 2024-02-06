package com.project.DigitalWallet.Controller;

import com.project.DigitalWallet.DTO.UserDTO;
import com.project.DigitalWallet.ErrorResponse;
import com.project.DigitalWallet.Model.Users;
import com.project.DigitalWallet.Service.walletServices;
import com.project.DigitalWallet.TransferRequest;
import com.project.DigitalWallet.repo.walletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("digitalWalletSystem/v1/wallet")

@RestController
public class walletController {
    @Autowired
    walletRepository wallet;

    @Autowired
    walletServices w;

    @PostMapping("/createWallet")
   public ResponseEntity<UserDTO> addUser(@RequestBody Users u) {
//        w.sendTransactionNotification("venugundam7378@gmail.com","Registration","Hi,You have successfully Registered");
        return w.createWallet(u);
   }


@PostMapping ("/loadMoney")
public ResponseEntity<?> addMoney(@RequestBody Users u) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()) {

        String username = authentication.getName();

        Optional<Users> user = wallet.findById(u.getUserId());
        if(user.isPresent()){
            String u_name_from_user=user.get().getUsername();
                    if(username.equals(u_name_from_user)){
                        w.saveTransaction(u.getUserId(), "Loaded", u.getBalance(), w.getDateTime());
                        return w.addMoney(u.getUserId(), u.getBalance());
                    }
                    return new ResponseEntity<>(new ErrorResponse("Invalid UserName or Password"),HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new ErrorResponse("No User Found with this userID"),HttpStatus.NOT_FOUND);


//        w.sendTransactionNotification("venugundam7378@gmail.com","Money Added Successfully","Rs"+u.getBalance()+" is added to your wallet  Successfully!! \n" +w.getDateTime());

    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
}


    @PostMapping(value ="/transferMoney", consumes={"application/json"})
    public ResponseEntity<?> transferMoney(@RequestBody TransferRequest t) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Long userId1 = t.getFromUserId();
            Long userId2 = t.getToUserId();
            String username = authentication.getName();
            Optional<Users> user = wallet.findById(userId1);
            if (user.isPresent()) {
                String u_name_from_user = user.get().getUsername();
                if (username.equals(u_name_from_user)) {
                    w.saveTransaction(userId1, "Debited", t.getBalance(), w.getDateTime());
                    w.saveTransaction(userId2, "Credited", t.getBalance(), w.getDateTime());
                    return w.transferMoney(userId1, userId2, t.getBalance());
                }
                return new ResponseEntity<>(new ErrorResponse("Invalid UserName or Password"),HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(new ErrorResponse("No User Found with this userID"),HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }



    @GetMapping("/fetchBalance")
    public ResponseEntity<?> checkBalance(@RequestParam Long userId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<Users> u=wallet.findById(userId);
            if(u.isPresent()) {
                String u_name = u.get().getUsername();
                if (username.equals(u_name)) {
                    return w.checkBalance(userId);
                }
                return new ResponseEntity<>(new ErrorResponse("Invalid UserName or Password"),HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(new ErrorResponse("No User Found with this userID"),HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }




    @GetMapping("/fetchHistory")
    public ResponseEntity<?> getHistory(@RequestParam Long userId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<Users> u=wallet.findById(userId);
            if(u.isPresent()) {
                String u_name = u.get().getUsername();
                if (username.equals(u_name)) {
                    return w.getHistory(userId);
                }
                return new ResponseEntity<>(new ErrorResponse("Invalid UserName or Password"),HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(new ErrorResponse("No User Found with this userID"),HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        w.sendTransactionNotification("venugundam7378@gmail.com","Statement", String.valueOf(w.getHistory(u.getUserId())));

    }



    @GetMapping("/transactions/filter")
    public ResponseEntity<?> filterTransactions( @RequestParam Long userId, @RequestParam String transactionType)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<Users> u=wallet.findById(userId);
            if(u.isPresent()) {
                String u_name = u.get().getUsername();
                if (username.equals(u_name)) {
                    return w.filterTransactions(userId,transactionType);
                }
                return new ResponseEntity<>(new ErrorResponse("Invalid UserName or Password"),HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(new ErrorResponse("No User Found with this userID"),HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }


    @DeleteMapping("/removeUser")
    public  ResponseEntity<?> removeUser(@RequestParam Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<Users> user=wallet.findById(userId);
            if(user.isPresent()) {
                String u_name = user.get().getUsername();
                if (username.equals(u_name)) {
                    return w.removeUser(userId);
                }
                return new ResponseEntity<>(new ErrorResponse("Invalid UserName or Password"),HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(new ErrorResponse("No User Found with this userID"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

}
