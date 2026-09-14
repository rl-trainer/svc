package com.versed.rl_trainer_svc.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.versed.rl_trainer_svc.dto.MeResponse;
import com.versed.rl_trainer_svc.service.UserService;

@RestController 
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/me")
    public MeResponse getMe(@AuthenticationPrincipal Long userId){
        return userService.getMeResponse(userId);
    }
}
