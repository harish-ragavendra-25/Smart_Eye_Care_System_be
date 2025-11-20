package com.example.Smart_Eye_Care_be.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Smart_Eye_Care_be.Models.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel,Long> {
    Optional<UserModel> findByEmail(String email);
    boolean existsByEmail(String email);
}
