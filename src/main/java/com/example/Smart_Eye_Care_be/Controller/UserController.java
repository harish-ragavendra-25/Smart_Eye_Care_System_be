package com.example.Smart_Eye_Care_be.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Smart_Eye_Care_be.Dtos.UserRequestDto;
import com.example.Smart_Eye_Care_be.Dtos.UserResponseDto;
import com.example.Smart_Eye_Care_be.Models.UserModel;
import com.example.Smart_Eye_Care_be.Service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private final UserService userService;

    @PostMapping("/create")
    public UserResponseDto add(@RequestBody UserRequestDto user){
        return userService.createUser(user);
    }

    @GetMapping("/")
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }




    
    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return userService.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
