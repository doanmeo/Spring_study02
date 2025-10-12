package com.hocSpring.bai_01.dto.Request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// import lombok.Getter;
// import lombok.Setter;
import lombok.experimental.FieldDefaults;

// @Getter
// @Setter
@Data // dung de tao getter setter toString hashCode equals
@Builder // dung de khoi tao doi tuong va gan gia tri nhanh gon hon
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE) // dat tat ca cac truong trong lop la private
public class UserCreationRequest {
    @Size(min = 3, message = "USER_NAME_INVALID")
    String username;
    @Size(min = 6, message = "PASSWORD_INVALID")
    String password;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;

}