package com.hocSpring.bai_01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hocSpring.bai_01.dtoRequest.UserCreationRequest;
import com.hocSpring.bai_01.dtoRequest.UserUpdateRequest;
import com.hocSpring.bai_01.entity.User;
import com.hocSpring.bai_01.exception.AppException;
import com.hocSpring.bai_01.exception.ErrorCode;
import com.hocSpring.bai_01.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository; // <-- corrected spelling

    public User createRequest(UserCreationRequest request) {
        User user = new User();
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.User_Exists);
        }

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        return userRepository.save(user); // <-- corrected spelling

    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public User updateUser(Long userId, UserUpdateRequest request) {

        User existingUser = getUserById(userId);
        existingUser.setPassword(request.getPassword());
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setDateOfBirth(request.getDateOfBirth());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
