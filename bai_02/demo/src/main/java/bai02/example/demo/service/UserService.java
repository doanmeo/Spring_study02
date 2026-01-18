package bai02.example.demo.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bai02.example.demo.dto.request.UserCreationRequest;
import bai02.example.demo.dto.request.UserUpdateRequest;
import bai02.example.demo.dto.response.UserResponse;
import bai02.example.demo.entity.User;
import bai02.example.demo.exception.AppException;
import bai02.example.demo.exception.ErrorCode;
import bai02.example.demo.mapper.UserMapper;
import bai02.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
// RequiredArgsConstructor: tự động tạo constructor cho tất cả các thuộc tính
// final hoặc có giá trị mặc định la NotNull
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.User_Existed);
        }
        User user = userMapper.toUser(request);

        PasswordEncoder encoder = new BCryptPasswordEncoder(10);// mã hóa mật khẩu với độ dài 10 ký tự
        user.setPassword(encoder.encode(request.getPassword()));

        // user.setUsername(request.getUsername());
        // user.setPassword(request.getPassword());
        // user.setEmail(request.getEmail());
        // user.setFirstName(request.getFirstName());
        // user.setLastName(request.getLastName());
        // user.setDob(request.getDob());

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUserById(String userid) {
        return userMapper.toUserResponse(userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(UserUpdateRequest request, String userid) {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // user.setPassword(request.getPassword());
        // user.setEmail(request.getEmail());
        // user.setFirstName(request.getFirstName());
        // user.setLastName(request.getLastName());
        // user.setDob(request.getDob());
        userMapper.updateUser(request, user);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userid) {
        userRepository.deleteById(userid);
    }

}
