package com.eParking.Egesha.api.controller.auth;

import com.eParking.Egesha.api.dto.SuccessAndMessage;
import com.eParking.Egesha.model.UserProfile;
import com.eParking.Egesha.model.dao.UserProfileRepository;
import com.eParking.Egesha.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private UserProfileRepository userProfileRepository;

    //should this emdpoint be a POST or PUT
    //I think it should be a and update user endpoint
    @PostMapping("/profile")
    public ResponseEntity<SuccessAndMessage> userProfile (@RequestPart("name") String name, @RequestPart("pictureFile" ) MultipartFile file) {
        SuccessAndMessage response =new SuccessAndMessage();
        try{
            String profilePicture = userProfileService.saveFile(file);
            UserProfile profile = new UserProfile();
            profile.setProfilePicture(profilePicture);
            profile.setFirstName(firstName);
            profile.setLastName(lastName);
            profile.setEmail(email);
            UserProfile savedProfile = userProfileRepository.save(profile);
            response.setSuccess(true);
            response.setMessage("Profile created successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (IOException e) {
            response.setSuccess(true);
            response.setMessage("Internal Server Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
