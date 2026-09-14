package com.versed.rl_trainer_svc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.versed.rl_trainer_svc.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByGoogleSub(String googleSub);
    
}
