package com.eParking.Egesha.service;

import com.eParking.Egesha.api.dto.*;
import com.eParking.Egesha.api.security.JWTGenerator;
import com.eParking.Egesha.model.LocalUser;
import com.eParking.Egesha.model.dao.AdminRepository;
import com.eParking.Egesha.model.dao.LocalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private LocalUserRepository localUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserTypeService userTypeService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTGenerator jwtGenerator;


    public ResponseEntity<SuccessAndMessage> updateUser(Integer userId, UserUpdate userUpdate){
        System.out.println("userUpdate");
        SuccessAndMessage response = new SuccessAndMessage();
        if (!(localUserRepository.existsById(userId))) {
            response.setMessage("User does not exist");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Optional<LocalUser> user = localUserRepository.findById(userId);
        LocalUser localUser = user.get();
        localUser.setFirstName(userUpdate.getFirstName());
        localUser.setLastName(userUpdate.getLastName());
        localUser.setPhoneNumber(userUpdate.getPhoneNumber());
        localUser.setEmail(userUpdate.getEmail());
        localUser.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        localUserRepository.save(localUser);
        response.setMessage("User Updated Successfully");
        response.setSuccess(true);
        return new ResponseEntity<SuccessAndMessage>(response, HttpStatus.OK);
    }

    public ResponseEntity<SuccessAndMessage> deleteUser (Integer userId) {
        SuccessAndMessage response = new SuccessAndMessage();
        if (!(localUserRepository.existsById(userId))) {
            response.setMessage("User does not exist");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        localUserRepository.deleteById(userId);
        response.setMessage("User deleted Successfully");
        response.setSuccess(true);
        return new ResponseEntity<SuccessAndMessage>(response, HttpStatus.OK);
    }

    public ResponseEntity<SuccessAndMessage> deleteAllUsers() {
        System.out.println("deleteAllUsers");
        SuccessAndMessage response = new SuccessAndMessage();
        localUserRepository.deleteAll();
        response.setMessage("Users deleted successfully");
        response.setSuccess(true);
        return new ResponseEntity<SuccessAndMessage>(response, HttpStatus.OK);
    }
}
