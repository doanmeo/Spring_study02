package com.hocSpring.bai_01.dto.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hocSpring.bai_01.dto.Respond.UserRespond;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

//chuan hoa api response
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL) // khong hien thi nhung thuoc tinh null
public class ApiResponse<T> {// T la kieu du lieu dong
     int code = 1000; // mac dinh la 1000-thanh cong
     String message;
     T result;

    

}
