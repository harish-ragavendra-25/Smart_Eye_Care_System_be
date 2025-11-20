package com.example.Smart_Eye_Care_be.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String userName;
    private String password;
    private String email;
    private String role;
}
