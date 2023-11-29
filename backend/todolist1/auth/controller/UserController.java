package com.backend.todolist1.auth.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.todolist1.auth.service.UserService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {	

    @Autowired
    private UserService userService;

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/api/auth/signin", method = RequestMethod.POST)
    public ResponseEntity<UserSigninResponse> signin(@Valid @RequestBody UserSigninRequest userSigninRequest,HttpSession session) {
    	 session.setAttribute("username", userSigninRequest.getUsername());
    	return new ResponseEntity<>(userService.signin(userSigninRequest), HttpStatus.OK);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/api/auth/signup", method = RequestMethod.POST)
    public ResponseEntity<UserSignupResponse> signup(@Valid @RequestBody UserSignupRequest userSignupRequest) {
        return new ResponseEntity<>(userService.signup(userSignupRequest), HttpStatus.OK);
    }
}
