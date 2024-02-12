package com.project.DigitalWallet.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "credentials")
public class PasswordEntity {

    @Id
    private Long userId;
    private String Password;

}
