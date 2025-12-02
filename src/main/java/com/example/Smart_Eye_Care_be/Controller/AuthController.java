package com.example.Smart_Eye_Care_be.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Smart_Eye_Care_be.Dtos.AuthResponse;
import com.example.Smart_Eye_Care_be.Dtos.LoginRequestDto;
import com.example.Smart_Eye_Care_be.Models.UserModel;
import com.example.Smart_Eye_Care_be.Service.JwtService;
import com.example.Smart_Eye_Care_be.Service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginDto) {

        UserModel user = userService.authenticate(loginDto.getUserName(), loginDto.getPassword());
        
        String token = jwtService.generateToken(user);
        
        return ResponseEntity.ok(new AuthResponse(token, user.getUserName(), user.getRole().name()));
    }
}
