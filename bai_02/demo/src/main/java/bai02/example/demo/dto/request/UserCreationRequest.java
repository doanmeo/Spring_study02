package bai02.example.demo.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
// import lombok.Getter;
import lombok.NoArgsConstructor;
// import lombok.Setter;
import lombok.experimental.FieldDefaults;

// @Getter
// @Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserCreationRequest {
    // @Data  // tương đương với @Getter + @Setter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor
    // @Builder // cung cấp builder pattern để tạo đối tượng UserCreationRequest : UserCreationRequest.builder().username("user").password("pass").build();


    // chú ý: các annotation validation chỉ hoạt động khi được sử dụng trong controller với @Valid
    @Size(min = 3, message = "User_Name_Invalid") // kiểm tra độ dài tên đăng nhập ;error message trỏ đến ErrorCode.java
     String username;
    @Size(min = 6, message = "Invalid_Password") // kiểm tra độ dài mật khẩu
     String password;
    @Email(message = "Email should be valid") // kiểm tra định dạng email
     String email;
     String firstName;
     String lastName;

     LocalDate dob;

}
