package com.eParking.Egesha.service;

import com.eParking.Egesha.api.dto.*;
import com.eParking.Egesha.api.security.WebSecurityConfig;
import com.eParking.Egesha.model.*;
import com.eParking.Egesha.api.security.JWTGenerator;
import com.eParking.Egesha.exception.UserAlreadyExistsException;
import com.eParking.Egesha.model.dao.AdminRepository;
import com.eParking.Egesha.model.dao.LocalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private LocalUserRepository localUserRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserTypeService userTypeService;
    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private LocalUser localUser;
    @Autowired
    WebSecurityConfig webSecurityConfig;

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
        userTypeService.setUserType(UserType.ADMIN);
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
        if (localUserRepository.findByPhoneNumber(registrationBody.getPhoneNumber()).isPresent()
                || localUserRepository.findByPhoneNumber(registrationBody.getPhoneNumber()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setPhoneNumber(registrationBody.getPhoneNumber());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setPassword(webSecurityConfig.passwordEncoder().encode(registrationBody.getPassword()));
        user = localUserRepository.save(user);
        return localUserRepository.save(user);
    }

    public ResponseEntity<UserLoginResponse> loginUser(@RequestBody LoginBody loginBody) {
        System.out.println("userLogin");
        userTypeService.setUserType(UserType.LOCALUSER);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginBody.getPhoneNumber(), loginBody.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication, UserType.LOCALUSER.toString());
        UserLoginResponse response= new UserLoginResponse();
        LocalUser user = localUserRepository.findByPhoneNumber(loginBody.getPhoneNumber()).orElseThrow();
        String encodedPassword = user.getPassword();
        String passedPassword = loginBody.getPassword();
        boolean passwordsMatch = passwordEncoder.matches(passedPassword, encodedPassword);
        if(passwordsMatch){
            response.setSuccess(true);
            response.setMessage("login successful");
            response.setToken(token);
            response.setUser(user.getFirstName(), user.getEmail(), user.getUserId(), user.getPhoneNumber(), user.getLastName());
            return new ResponseEntity<UserLoginResponse>(response, HttpStatus.OK);
        }
        response.setSuccess(false);
        response.setMessage("Incorrect PhoneNumber or password");
        return new ResponseEntity<UserLoginResponse>(response, HttpStatus.UNAUTHORIZED);
    }

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
}
