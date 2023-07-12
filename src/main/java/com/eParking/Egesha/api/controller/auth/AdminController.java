package com.eParking.Egesha.api.controller.auth;

import com.eParking.Egesha.api.dto.*;
import com.eParking.Egesha.exception.UserAlreadyExistsException;
import com.eParking.Egesha.service.AdminService;
import com.eParking.Egesha.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/auth")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;


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
            response.setMessage("Phone Number Already Registered");
            response.setSuccess(true);
            return new ResponseEntity<SuccessAndMessage>(response,HttpStatus.OK);
        }
    }

    @PutMapping("/updateUser/{userId}")
        public ResponseEntity<SuccessAndMessage> updateUser (@PathVariable Integer userId, @RequestBody UserUpdate userUpdate, @RequestHeader(name="Authorization") String token) {
            System.out.println("userUpdate");
            return userService.updateUser(userId, userUpdate);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<SuccessAndMessage> deleteUser(@PathVariable Integer userId, @RequestHeader(name="Authorization") String token) {
        System.out.println("deleteUser");
        return adminService.deleteUser(userId);
    }

    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<SuccessAndMessage> deleteAllUsers(@RequestHeader(name="Authorization") String token) {
        System.out.println("deleteAllUsers");
        return adminService.deleteAllUsers();
    }

//    @GetMapping("/allUsers")
//    public ResponseEntity<AllUsersResponse> allUsers(@RequestHeader(name="Authorization") String token) {
//        System.out.println("allUsers");
//        return adminService.allUsers();
//    }

}
