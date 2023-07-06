package com.eParking.Egesha.service;

import com.eParking.Egesha.api.dto.*;
import com.eParking.Egesha.api.security.JWTGenerator;
import com.eParking.Egesha.exception.UserAlreadyExistsException;
import com.eParking.Egesha.model.Admin;
import com.eParking.Egesha.model.LocalUser;
import com.eParking.Egesha.model.UserType;
import com.eParking.Egesha.model.dao.AdminRepository;
import com.eParking.Egesha.model.dao.LocalUserRepository;
import com.eParking.Egesha.api.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private LocalUserRepository localUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
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
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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

    public ResponseEntity<AllUsersResponse> allUsers() {
        Iterable<LocalUser> usersIterable = localUserRepository.findAll();
        List<LocalUser> users = StreamSupport.stream(usersIterable.spliterator(), false)
                .collect(Collectors.toList());

        AllUsersResponse allUsersResponse = new AllUsersResponse();
        ArrayList<UserDetails> userDetails = new ArrayList<>();

        if (!users.isEmpty()) {
            allUsersResponse.setMessage("All users found");
            allUsersResponse.setSuccess(true);

            for (LocalUser user : users) {
                UserDetails userDetail = new UserDetails();
                userDetail.setUserId(user.getUserId());
                userDetail.setFirstName(user.getFirstName());
                userDetail.setLastName(user.getLastName());
                userDetail.setPhoneNumber(user.getPhoneNumber());
                userDetail.setEmail(user.getEmail());
                userDetails.add(userDetail);
            }

            allUsersResponse.setUsers(userDetails);
            return ResponseEntity.ok().body(allUsersResponse);
        }

        allUsersResponse.setSuccess(false);
        allUsersResponse.setMessage("No Users Found");
        return ResponseEntity.badRequest().body(allUsersResponse);
    }
}
