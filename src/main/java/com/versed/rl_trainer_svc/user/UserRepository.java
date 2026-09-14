package com.versed.rl_trainer_svc.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByGoogleSub(String googleSub);
    
}
