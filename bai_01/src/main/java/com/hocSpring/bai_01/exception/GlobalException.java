package com.hocSpring.bai_01.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hocSpring.bai_01.dto.Request.ApiResponse;

@ControllerAdvice
public class GlobalException {

    // @ExceptionHandler(value = RuntimeException.class) // Bat loi RuntimeException tren toan ung dung
    // ResponseEntity<ApiResponse> handlingRunTimException(RuntimeException e) {

    //     ApiResponse apiResponse = new ApiResponse();
    //     apiResponse.setCode(ErrorCode.UNCATEGORIZED.getCode()); // Ma loi khong xac dinh
    //     apiResponse.setMessage(ErrorCode.UNCATEGORIZED.getMessage()); // Thong diep loi
    //     return ResponseEntity.badRequest().body(apiResponse); // Tra ve ma loi 400 (Bad Request)

    // }

    @ExceptionHandler(value = RuntimeException.class) // Bat loi RuntimeException tren toan ung dung
    ResponseEntity<ApiResponse> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(apiResponse); // Tra ve ma loi 400 (Bad Request)

    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String enumKey = e.getFieldError().getDefaultMessage(); // Lay thong diep loi tu annotation @Size
        
        ErrorCode errorCode = ErrorCode.Invalid_KEY; // Mac dinh la loi Invalid_KEY
        try {
            errorCode = ErrorCode.valueOf(enumKey); // Chuyen doi thong diep loi thanh enum ErrorCode neu co trong enum
        } catch (IllegalArgumentException ex) {
            
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode()); // Lay ma loi tu enum ErrorCode
        apiResponse.setMessage(errorCode.getMessage()); // Lay thong diep loi tu enum ErrorCode
        return ResponseEntity.badRequest().body(apiResponse); // Tra ve ma loi 400 (Bad Request)
    }

}
