package com.hocSpring.bai_01.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hocSpring.bai_01.dto.Request.ApiResponse;
import com.hocSpring.bai_01.dto.Request.AuthenticationRequest;
import com.hocSpring.bai_01.dto.Request.IntroSpectRequest;
import com.hocSpring.bai_01.dto.Respond.AuthenticationRespond;
import com.hocSpring.bai_01.dto.Respond.IntroSpectRespond;
import com.hocSpring.bai_01.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.var;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth") // Định nghĩa đường dẫn cơ sở cho tất cả các endpoint trong controller này
@RequiredArgsConstructor // Tự động tạo constructor cho các field final
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    
    @PostMapping("/login")
    ApiResponse<AuthenticationRespond> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationRespond>builder()
                .result(result).build();
                       
                       
    }
    @PostMapping("/intro-spect")
    ApiResponse<IntroSpectRespond> authenticate(@RequestBody IntroSpectRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<IntroSpectRespond>builder()
                .result(result).build();
                       
                       
    }


}
