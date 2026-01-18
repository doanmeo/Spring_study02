package bai02.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import bai02.example.demo.dto.request.ApiResponse;

//ControllerAdvice: xử lý ngoại lệ toàn cục cho tất cả các controller trong ứng dụng
@ControllerAdvice
public class GlobalExceptionHandeler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex) {
        // trả về mã lỗi 400 (Bad Request) và thông báo lỗi
        ApiResponse response = new ApiResponse();
        response.setCode(400);
        response.setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException ex) {

        ApiResponse response = new ApiResponse();

        String enumKey = ex.getFieldError().getDefaultMessage();
        try {
            ErrorCode test = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            enumKey = "Uncategorize_Error";
        }
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);        
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException ex) {

        // các lớp xử lý ngoại lệ: ErrorCode -> AppException -› GlobalExceptionHandler
                                    // ↘ ApiResponse

        ApiResponse response = new ApiResponse();
        response.setCode(ex.getErrorCode().getCode());
        response.setMessage(ex.getErrorCode().getMessage());

        return ResponseEntity.badRequest().body(response);
    }
}
