package com.eParking.Egesha.api.controller.auth;

import com.eParking.Egesha.api.dto.*;
import com.eParking.Egesha.api.security.JWTGenerator;
import com.eParking.Egesha.exception.UserAlreadyExistsException;
import com.eParking.Egesha.model.Admin;
import com.eParking.Egesha.model.LocalUser;
import com.eParking.Egesha.model.UserType;
import com.eParking.Egesha.model.dao.AdminRepository;
import com.eParking.Egesha.service.CustomUserDetailsService;
import com.eParking.Egesha.model.dao.LocalUserRepository;
import com.eParking.Egesha.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    UserService userService;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    LocalUserRepository localUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTGenerator jwtGenerator;
    SuccessAndMessage successAndMessage;

    @PostMapping("/registerAdmin")
    public ResponseEntity registerAdmin (@Valid @RequestBody AdminRegistration adminRegistration) {
        if(adminRepository.existsByUsername(adminRegistration.getUsername())) {
            return new ResponseEntity<String>("Username Taken", HttpStatus.BAD_REQUEST);
        }
        Admin admin = new Admin();
        admin.setUsername(adminRegistration.getUsername());
        admin.setPassword(passwordEncoder.encode(adminRegistration.getPassword()));
        adminRepository.save(admin);
        return new ResponseEntity<String>("Admin Registered Successfully", HttpStatus.CREATED);
    }

    @PostMapping("/loginAdmin")
    public ResponseEntity<AdminLoginResponse> login(@Valid @RequestBody AdminLoginBody adminLoginBody) {
        System.out.println("adminLogin");
        customUserDetailsService.setUserType(UserType.ADMIN);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(adminLoginBody.getUsername(), adminLoginBody.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication, UserType.ADMIN.toString());
        AdminLoginResponse response = new AdminLoginResponse();
        response.setSuccess(true);
        response.setMessage("login Successful");
        response.setToken(token);
        Admin admin = adminRepository.findByUsername(adminLoginBody.getUsername()).orElseThrow();
        response.setAdmin(admin.getUsername(), admin.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<SuccessAndMessage> registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            SuccessAndMessage response= new SuccessAndMessage();
            userService.registerUser(registrationBody);
            response.setMessage("User Registered Successfully");
            response.setSuccess(true);
            return new ResponseEntity<SuccessAndMessage>(response,HttpStatus.OK);
        } catch (UserAlreadyExistsException e) {
            SuccessAndMessage response = new SuccessAndMessage();
            response.setMessage("Email Already Registered");
            response.setSuccess(true);
            return new ResponseEntity<SuccessAndMessage>(response,HttpStatus.OK);
        }
    }
    @PostMapping("loginUser")
    public ResponseEntity<UserLoginResponse> loginUser(@RequestBody LoginBody loginBody) {
        System.out.println("userLogin");
        return userService.loginUser(loginBody);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<SuccessAndMessage> updateUser (@PathVariable Integer userId,@Valid @RequestBody UserUpdate userUpdate, @RequestHeader(name="Authorization") String jwt) {
        System.out.println("userUpdate");
        return userService.updateUser(userId, userUpdate);
    }
}