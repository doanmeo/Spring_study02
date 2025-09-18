package com.hocSpring.bai_01.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.hocSpring.bai_01.dtoRequest.ApiResponse;
import com.hocSpring.bai_01.dtoRequest.UserCreationRequest;
import com.hocSpring.bai_01.dtoRequest.UserUpdateRequest;
import com.hocSpring.bai_01.entity.User;
import com.hocSpring.bai_01.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) { 
        ApiResponse<User> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createRequest(request));
        
        
        return apiResponse;
    }

    @GetMapping
    List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") Long userId) {

        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    User updateUser(@RequestBody UserUpdateRequest request, @PathVariable("userId") Long userId) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "Deleted user with id: " + userId;
    }

}
