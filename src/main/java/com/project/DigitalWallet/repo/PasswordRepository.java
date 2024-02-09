package com.project.DigitalWallet.repo;
import com.project.DigitalWallet.Model.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<PasswordEntity,Long> {
}
