package bai02.example.demo.exception;

public enum ErrorCode {
    User_Existed(1001, "User already exists"),
    User_Not_Existed(1002, "User not found"),
    Uncategorize_Error(9999, "Uncategorize error"),
    User_Name_Invalid(1003, "Username must be at least 3 characters"),
    Invalid_Password(1004, "Password must be at least 6 characters"),
    Invalid_Credentials(1005, "Invalid username or password"),
    User_Not_Found(1009, "User not found");

    private String message;
    private int code;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}