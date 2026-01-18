package bai02.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bai02.example.demo.dto.request.ApiResponse;
import bai02.example.demo.dto.request.AuthenticationRequest;
import bai02.example.demo.dto.request.IntrospectRequest;
import bai02.example.demo.dto.response.AuthenticationResponse;
import bai02.example.demo.dto.response.IntrorespectResponse;
import bai02.example.demo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    // controller -> service -> repository -> database
    // dto_request -> entity
                                // request     -> response     
    // ApiResponse -> AuthenticationResponse: chứa thông tin trả về cho client


    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var isAuthenticated = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder() //builder() để tạo đối tượng ApiResponse
                .result(isAuthenticated)
                .build();
        
    }

    @PostMapping("/introspect") //ham nhan token tu client de kiem tra tinh hop le cua token
    ApiResponse<IntrorespectResponse> introspect(@RequestBody IntrospectRequest request) {
        //controller goi service de kiem tra tinh hop le cua token; request -> service ->response -> controller -> client
        var isAuthenticated = authenticationService.introspect(request);
        return ApiResponse.<IntrorespectResponse>builder() //builder() để tạo đối tượng ApiResponse
                .result(isAuthenticated)
                .build();
        
    }




}
