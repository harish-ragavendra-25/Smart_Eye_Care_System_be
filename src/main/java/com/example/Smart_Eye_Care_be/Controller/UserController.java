package com.example.Smart_Eye_Care_be.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Smart_Eye_Care_be.Dtos.UserRequestDto;
import com.example.Smart_Eye_Care_be.Dtos.UserResponseDto;
import com.example.Smart_Eye_Care_be.Models.UserModel;
import com.example.Smart_Eye_Care_be.Service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:5173", allowCredentials = true)
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private final UserService userService;

    @PostMapping("/")
    public UserResponseDto add(@RequestBody UserRequestDto user){
        return userService.createUser(user);
    }
}
