package com.eParking.Egesha.service;

import com.eParking.Egesha.api.model.*;
import com.eParking.Egesha.api.security.JWTGenerator;
import com.eParking.Egesha.exception.UserAlreadyExistsException;
import com.eParking.Egesha.model.Admin;
import com.eParking.Egesha.model.LocalUser;
import com.eParking.Egesha.model.UserType;
import com.eParking.Egesha.model.dao.AdminRepository;
import com.eParking.Egesha.model.dao.LocalUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

public class AdminService {
    private AdminRepository adminRepository;
    private LocalUserRepository localUserRepository;
    private PasswordEncoder passwordEncoder;
    private CustomUserDetailsService customUserDetailsService;
    private AuthenticationManager authenticationManager;
    private JWTGenerator jwtGenerator;
    private EncryptionService encryptionService;

    public ResponseEntity<String> registerAdmin (AdminRegistration adminRegistration) {
        if (adminRepository.existsByUsername(adminRegistration.getUsername())) {
            return new ResponseEntity<>("Username Taken", HttpStatus.BAD_REQUEST);
        }
        Admin admin = new Admin();
        admin.setUsername(adminRegistration.getUsername());
        admin.setPassword(passwordEncoder.encode(adminRegistration.getPassword()));
        adminRepository.save(admin);
        return new ResponseEntity<>("Admin Registration Successful", HttpStatus.CREATED);
    }

    public ResponseEntity<AdminLoginResponse> loginAdmin(@RequestBody AdminLoginBody adminLoginBody) {
        System.out.println("loginAdmin");
        customUserDetailsService.setUserType(UserType.ADMIN);
        ;
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(adminLoginBody.getUsername(), adminLoginBody.getPassword()));

        String token = jwtGenerator.generateToken(authentication, UserType.ADMIN.toString());
        AdminLoginResponse response = new AdminLoginResponse();
        Admin admin = adminRepository.findByUsername(adminLoginBody.getUsername()).orElseThrow();
        String encodePassword = admin.getPassword();
        String passedPassword = adminLoginBody.getPassword();
        boolean passwordMatch = passwordEncoder.matches(passedPassword, encodePassword);
        if (passwordMatch) {
            response.setSuccess(true);
            response.setMessage("login successful");
            response.setToken(token);
            response.setAdmin(admin.getUsername(), admin.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setSuccess(false);
        response.setMessage("Incorrect username or password");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if (localUserRepository.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || localUserRepository.findByPhoneNumber(registrationBody.getPhoneNumber()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setPhoneNumber(registrationBody.getPhoneNumber());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        user = localUserRepository.save(user);
        return localUserRepository.save(user);
    }

}
