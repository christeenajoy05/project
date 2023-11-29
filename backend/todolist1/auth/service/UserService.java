package com.backend.todolist1.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.todolist1.auth.controller.UserSigninRequest;
import com.backend.todolist1.auth.controller.UserSigninResponse;
import com.backend.todolist1.auth.controller.UserSignupRequest;
import com.backend.todolist1.auth.controller.UserSignupResponse;
import com.backend.todolist1.auth.model.User;
import com.backend.todolist1.auth.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserSignupResponse signup(UserSignupRequest userSignupRequest) {
        try {
            String username = userSignupRequest.getUsername();
            String password = userSignupRequest.getPassword();

            User user = userRepository.findByUsername(username);
            if (user != null) {
            	 throw new RuntimeException("Username is already taken");
            }

            User _user = new User(username, password); // Note: No password encoding for simplicity
            _user = userRepository.save(_user);

            // Omitting token creation for simplicity

            return new UserSignupResponse(username, "OmittedToken"); // Omitted token for simplicity
        } catch (Exception e) {
            // Handle exceptions
            throw new RuntimeException("Error during signup", e);
        }
    }

    public UserSigninResponse signin(UserSigninRequest userSigninRequest) {
        try {
            String username = userSigninRequest.getUsername();
            // Omitting authentication for simplicity

            // Omitting token creation for simplicity

            return new UserSigninResponse(username, "OmittedToken"); // Omitted token for simplicity
        } catch (Exception e) {
            // Handle exceptions
            throw new RuntimeException("Error during signin", e);
        }
    }
}
