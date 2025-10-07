package com.hocSpring.bai_01.dto.Respond;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserRespond {
     Long id;
     String username;
     String password;
     String firstName;
     String lastName;
     LocalDate dateOfBirth;


}
