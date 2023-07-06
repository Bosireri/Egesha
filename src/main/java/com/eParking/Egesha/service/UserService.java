package com.eParking.Egesha.service;

import com.eParking.Egesha.api.dto.LoginBody;
import com.eParking.Egesha.api.dto.RegistrationBody;
import com.eParking.Egesha.api.dto.SuccessAndMessage;
import com.eParking.Egesha.api.dto.UserUpdate;
import com.eParking.Egesha.exception.UserAlreadyExistsException;
import com.eParking.Egesha.model.LocalUser;
import com.eParking.Egesha.model.dao.LocalUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private LocalUserRepository localUserRepository;
    private EncryptionService encryptionService;
    private JWTService jwtService;
    private PasswordEncoder passwordEncoder;

    public UserService(LocalUserRepository localUserRepository, EncryptionService encryptionService, JWTService jwtService) {
        this.localUserRepository = localUserRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
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

    public  String loginUser(LoginBody loginBody) {
        Optional<LocalUser> opUser = localUserRepository.findByPhoneNumber(loginBody.getPhoneNumber());
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }
        return null;
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
