package com.hocSpring.bai_01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hocSpring.bai_01.Mapper.UserMapper;
import com.hocSpring.bai_01.dto.Request.UserCreationRequest;
import com.hocSpring.bai_01.dto.Request.UserUpdateRequest;
import com.hocSpring.bai_01.dto.Respond.UserRespond;
import com.hocSpring.bai_01.entity.User;
import com.hocSpring.bai_01.exception.AppException;
import com.hocSpring.bai_01.exception.ErrorCode;
import com.hocSpring.bai_01.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository; // <-- corrected spelling
    @Autowired
    private UserMapper userMapper;

    public UserRespond createRequest(UserCreationRequest request) {
        
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.User_Exists);
        }
        User user = userMapper.toUser(request);
        // User user = new User();
        // user.setUsername(request.getUsername());
        // user.setPassword(request.getPassword());
        // user.setFirstName(request.getFirstName());
        // user.setLastName(request.getLastName());
        // user.setDateOfBirth(request.getDateOfBirth());

        //bcrypt password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toUserRespond(userRepository.save(user)); 

    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserRespond getUserById(Long userId) {
        return userMapper.toUserRespond(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId)));
    }

    public UserRespond updateUser(Long userId, UserUpdateRequest request) {

        User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        userMapper.updateUser(request, existingUser);
        // existingUser.setPassword(request.getPassword());
        // existingUser.setFirstName(request.getFirstName());
        // existingUser.setLastName(request.getLastName());
        // existingUser.setDateOfBirth(request.getDateOfBirth());
        return userMapper.toUserRespond(userRepository.save(existingUser));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
