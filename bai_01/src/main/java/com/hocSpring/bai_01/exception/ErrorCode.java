package com.hocSpring.bai_01.exception;

import jakarta.persistence.criteria.CriteriaBuilder.In;

public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized error"),
    User_Exists(1001, "User already exists"),
    Invalid_KEY(1004, "Invalid message key for validation"),
    USER_NAME_INVALID(1002, "Username must be at least 3 characters long"),
    PASSWORD_INVALID(1003, "Password must be at least 6 characters long");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
