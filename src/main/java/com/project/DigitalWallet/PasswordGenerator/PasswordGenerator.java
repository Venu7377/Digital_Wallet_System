package com.project.DigitalWallet.PasswordGenerator;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class PasswordGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    public  String generateUniquePassword() {
        byte[] randomBytes = new byte[4];
        secureRandom.nextBytes(randomBytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
