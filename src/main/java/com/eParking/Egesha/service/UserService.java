package com.eParking.Egesha.service;

import com.eParking.Egesha.api.model.LoginBody;
import com.eParking.Egesha.api.model.RegistrationBody;
import com.eParking.Egesha.exception.UserAlreadyExistsException;
import com.eParking.Egesha.model.LocalUser;
import com.eParking.Egesha.model.dao.LocalUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private LocalUserRepository localUserRepository;
    private EncryptionService encryptionService;
    private JWTService jwtService;

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
}
