package com.eParking.Egesha.service;

import com.eParking.Egesha.api.model.RegistrationBody;
import com.eParking.Egesha.exception.UserAlreadyExistsException;
import com.eParking.Egesha.model.LocalUser;
import com.eParking.Egesha.model.dao.LocalUserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private LocalUserDAO localUserDAO;

    public UserService(LocalUserDAO localUserDAO) {
        this.localUserDAO = localUserDAO;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setFullName(registrationBody.getFullName());
        user.setUsername(registrationBody.getUsername());
        //TODO: Encrypt passwords!!
        user.setPassword(registrationBody.getPassword());
        user = localUserDAO.save(user);
        return user;
    }

}
