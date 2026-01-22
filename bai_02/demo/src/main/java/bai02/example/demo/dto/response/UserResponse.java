package bai02.example.demo.dto.response;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserResponse {
     String id;
     String username;
     // String password; // Thông thường không bao gồm mật khẩu trong phản hồi DTO
     String email;
     String firstName;
     String lastName;
     LocalDate dob;
     Set<String> roles;

}
