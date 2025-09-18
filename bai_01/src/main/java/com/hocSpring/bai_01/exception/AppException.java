package com.hocSpring.bai_01.exception;

public class AppException extends RuntimeException {
    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }   

    public ErrorCode getErrorCode() {
        return errorCode;
    }


}
