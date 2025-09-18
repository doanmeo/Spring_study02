package com.hocSpring.bai_01.dtoRequest;

import com.fasterxml.jackson.annotation.JsonInclude;

//chuan hoa api response
@JsonInclude(JsonInclude.Include.NON_NULL) // khong hien thi nhung thuoc tinh null
public class ApiResponse<T> {// T la kieu du lieu dong
    private int code = 1000; // mac dinh la 1000-thanh cong
    private String message;
    private T result;

    public ApiResponse() {
    }

    public ApiResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
