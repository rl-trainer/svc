package com.versed.rl_trainer_svc.service;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.versed.rl_trainer_svc.dto.MeResponse;
import com.versed.rl_trainer_svc.model.Points;
import com.versed.rl_trainer_svc.model.User;
import com.versed.rl_trainer_svc.repository.PointsRepository;
import com.versed.rl_trainer_svc.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PointsRepository pointsRepository;

    public UserService(UserRepository userRepository, PointsRepository pointsRepository) {
        this.userRepository = userRepository;
        this.pointsRepository = pointsRepository;
    }

    @Transactional
    public User findOrCreateFromGoogle(OidcUser oidcUser) {
        String googleSub = oidcUser.getSubject();
        User appUser = userRepository.findByGoogleSub(googleSub).orElseGet(()->{
            String email = oidcUser.getEmail();
            User newUser = new User();

            newUser.setGoogleSub(googleSub);
            newUser.setEmail(email);
            newUser.setUsername(oidcUser.getFullName());
            newUser.setAvatarUrl(oidcUser.getPicture());

            Instant now = Instant.now();
            newUser.setCreatedAt(now);
            newUser.setUpdatedAt(now);

            newUser.setIsVerified(Boolean.TRUE.equals(oidcUser.getEmailVerified()));

            User savedUser = userRepository.save(newUser);

            Points points = new Points();
            points.setBalance(0L);
            points.setMultiplier(BigDecimal.ONE);
            points.setUser(savedUser);
            pointsRepository.save(points);

            return savedUser;
        });
        return appUser;
    }

    @Transactional(readOnly = true)
    public MeResponse getMeResponse(Long userId){
        User appUser = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        MeResponse response = new MeResponse(appUser.getUsername(), appUser.getEmail(), appUser.getAvatarUrl(), appUser.getPoints().getBalance());

        return response;
    }

}
