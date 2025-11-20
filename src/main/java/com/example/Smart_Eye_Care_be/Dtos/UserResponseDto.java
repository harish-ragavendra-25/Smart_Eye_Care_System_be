package com.example.Smart_Eye_Care_be.Dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String userName;
    private String email;
    private String role;       // convert enum → string
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
