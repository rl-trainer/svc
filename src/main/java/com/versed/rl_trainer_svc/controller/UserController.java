package com.versed.rl_trainer_svc.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.versed.rl_trainer_svc.dto.MeResponse;
import com.versed.rl_trainer_svc.security.AuthCookieService;
import com.versed.rl_trainer_svc.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final AuthCookieService authCookieService;

    public UserController(UserService userService, AuthCookieService authCookieService) {
        this.userService = userService;
        this.authCookieService = authCookieService;
    }

    @GetMapping("/me")
    public MeResponse getMe(@AuthenticationPrincipal Long userId) {
        return userService.getMeResponse(userId);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(){
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, authCookieService.clearAccessTokenCookie().toString())
                .build();
    }
}
