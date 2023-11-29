package com.backend.todolist1.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.todolist1.auth.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}