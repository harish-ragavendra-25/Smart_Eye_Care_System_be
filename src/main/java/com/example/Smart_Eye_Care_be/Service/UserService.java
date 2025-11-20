package com.example.Smart_Eye_Care_be.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Smart_Eye_Care_be.Dtos.UserRequestDto;
import com.example.Smart_Eye_Care_be.Dtos.UserResponseDto;
import com.example.Smart_Eye_Care_be.Models.UserModel;
import com.example.Smart_Eye_Care_be.Repository.UserRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    @Autowired
    private final UserRepo userRepo;

    private UserModel.Role validateRole(String role) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        try {
            return UserModel.Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private UserModel mapToEntity(UserRequestDto dto) {
        UserModel user = new UserModel();
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setRole(validateRole(dto.getRole()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    private UserResponseDto mapToResponse(UserModel user) {
        return new UserResponseDto(
            user.getUserId(),
            user.getUserName(),
            user.getEmail(),
            user.getRole().name(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }


    @Transactional
    public UserResponseDto createUser(UserRequestDto userReq){
        validateEmail(userReq.getEmail());
        if (userRepo.existsByEmail(userReq.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userReq.getPassword() == null || userReq.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        UserModel savedUser = userRepo.save(mapToEntity(userReq));
        return mapToResponse(savedUser);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {
        UserModel user = userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + id));
        return mapToResponse(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto req) {

        UserModel existing = userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + id));

        // EMAIL
        if (req.getEmail() != null && !req.getEmail().isBlank()) {
            validateEmail(req.getEmail());

            if (!req.getEmail().equals(existing.getEmail()) && userRepo.existsByEmail(req.getEmail())) {
                throw new IllegalArgumentException("Email already exists: " + req.getEmail());
            }

            existing.setEmail(req.getEmail());
        }

        // USERNAME
        if (req.getUserName() != null && !req.getUserName().isBlank()) {
            existing.setUserName(req.getUserName());
        }

        // ROLE
        if (req.getRole() != null && !req.getRole().isBlank()) {
            existing.setRole(validateRole(req.getRole()));
        }

        // PASSWORD
        if (req.getPassword() != null && !req.getPassword().isBlank()) {

            if (req.getPassword().length() < 6) {
                throw new IllegalArgumentException("Password must be at least 6 characters");
            }

            existing.setPassword(req.getPassword());
        }

        existing.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(userRepo.save(existing));
    }

    @Transactional
    public String deleteUser(Long id) {
        UserModel user = userRepo.findById(id)
            .orElseThrow(() -> new NoSuchElementException("User not found: " + id));

        String deletedUserName = user.getUserName();
        userRepo.delete(user);
        return deletedUserName + " successfully deleted";
    }
    
}
