package bai02.example.demo.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bai02.example.demo.dto.request.UserCreationRequest;
import bai02.example.demo.dto.request.UserUpdateRequest;
import bai02.example.demo.dto.response.UserResponse;
import bai02.example.demo.entity.User;
import bai02.example.demo.enums.Role;
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
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);   
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')") 
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostAuthorize("returnObject.username == authentication.name or hasRole('ADMIN')")
    public UserResponse getUserById(String userid) {
        return userMapper.toUserResponse(userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse getMyInfo() {
       var context = SecurityContextHolder.getContext(); // Lấy ngữ cảnh bảo mật hiện tại
       String username = context.getAuthentication().getName(); // Lấy thông tin xác thực từ ngữ cảnh
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toUserResponse(user);// Chuyển đổi thực thể User thành UserResponse và trả về kết quả

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
