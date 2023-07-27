package com.eParking.Egesha.api.controller.auth;

import com.eParking.Egesha.api.dto.*;
import com.eParking.Egesha.api.security.JWTGenerator;
import com.eParking.Egesha.model.LocalUser;
import com.eParking.Egesha.model.dao.AdminRepository;
import com.eParking.Egesha.model.dao.LocalUserRepository;
import com.eParking.Egesha.service.AdminService;
import com.eParking.Egesha.service.UserService;
import io.jsonwebtoken.io.IOException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/admin/auth")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    LocalUserRepository localUserRepository;
    LocalUser user;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    JWTGenerator jwtGenerator;

// TODO (Thursday)
    @PostMapping("/registerUser")
    public ResponseEntity<SuccessAndMessage> registerUser(@Valid @RequestBody RegistrationBody registrationBody,
                                                                         @RequestHeader (name = "Authorization") String token) throws IOException {
        SuccessAndMessage response = new SuccessAndMessage();
        if(localUserRepository.existsByPhoneNumber(registrationBody.getPhoneNumber())) {
            response.setMessage("Phone Number is already registered");
            response.setSuccess(false);
            return new ResponseEntity<SuccessAndMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user = new LocalUser();
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setPhoneNumber(registrationBody.getPhoneNumber());
        user.setEmail(registrationBody.getEmail());
        user.setPassword(passwordEncoder.encode(registrationBody.getPassword()));
        localUserRepository.save(user);
        response.setMessage("User Registered Successfully");
        response.setSuccess(true);
        return new ResponseEntity<SuccessAndMessage>(response, HttpStatus.OK);
    }

    @PutMapping("/updateUser/{userId}")
        public ResponseEntity<SuccessAndMessage> updateUser (@PathVariable Integer userId,
                                                                            @RequestBody UserUpdate userUpdate,
                                                                            @RequestHeader(name="Authorization") String token) {
            System.out.println("userUpdate");
            return userService.updateUser(userId, userUpdate);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<SuccessAndMessage> deleteUser(@PathVariable Integer userId,
                                                                       @RequestHeader(name="Authorization") String token) {
        System.out.println("deleteUser");
        return adminService.deleteUser(userId);
    }

    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<SuccessAndMessage> deleteAllUsers(@RequestHeader(name="Authorization") String token) {
        System.out.println("deleteAllUsers");
        return adminService.deleteAllUsers();
    }

    @GetMapping("/allUsers")
    public List<LocalUser> findAllLocalUsers(){
        return localUserRepository.findAll();
    }

}

