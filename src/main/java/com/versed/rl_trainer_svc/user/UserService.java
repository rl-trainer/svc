package com.versed.rl_trainer_svc.user;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            points.setBalance(0);
            points.setMultiplier(BigDecimal.ONE);
            points.setUser(savedUser);
            pointsRepository.save(points);

            return savedUser;
        });
        return appUser;
    }
}
