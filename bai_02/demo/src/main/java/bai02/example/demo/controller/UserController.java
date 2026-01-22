package bai02.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import bai02.example.demo.dto.request.ApiResponse;
import bai02.example.demo.dto.request.UserCreationRequest;
import bai02.example.demo.dto.request.UserUpdateRequest;
import bai02.example.demo.dto.response.UserResponse;
import bai02.example.demo.entity.User;
import bai02.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    // controller -> service -> repository -> database
    // dto_request -> entity

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        // @RequestBody: lấy dữ liệu từ body của request và gán vào đối tượng request
        // @Valid: kiểm tra dữ liệu có hợp lệ không
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("User created successfully");
        response.setResult(userService.createUser(request));
        return response;
    }
    
    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @GetMapping
    ApiResponse<List<User>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication(); // Lấy người dùng đã xác thực
        log.info("Authenticated user: {}", authentication.getName());
        authentication.getAuthorities().forEach(authority -> log.info("Authority: {}", authority.getAuthority()));

        return ApiResponse.<List<User>>builder()
                .result(userService.getUsers())
                .build();
    }

    // thay vì trả về User, ta trả về UserResponse để ẩn thông tin nhạy cảm
    @GetMapping("/{userid}")
    UserResponse getUserById(@PathVariable("userid") String userid) {
        // @PathVariable: lấy dữ liệu từ đường dẫn và gán vào biến userid
        return userService.getUserById(userid);
    }

    @PutMapping("/{userid}")
    UserResponse updateUser(@RequestBody UserUpdateRequest request, @PathVariable("userid") String userid) {
        return userService.updateUser(request, userid);

    }

    @DeleteMapping("/{userid}")
    String deleteUser(@PathVariable("userid") String userid) {
        userService.deleteUser(userid);
        return "User deleted successfully";
    }

}
