package com.project.DigitalWallet.repo;

import com.project.DigitalWallet.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface walletRepository extends JpaRepository<Users,Long> {

}
