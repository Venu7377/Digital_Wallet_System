package com.project.DigitalWallet.Model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity
@Table(name = "users")
@Valid
@Component
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long userId;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 30, message = "Name cannot exceed 30 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name should contain only alphabets")
    private String name;

    @Size(min = 10, max = 10, message = "Contact number should be exactly 10 characters long")
    @Pattern(regexp = "^[0-9]+$", message = "Contact number should contain only numeric values")
    @Column(unique = true)
    private String contactNumber;
    private double amount;



}
