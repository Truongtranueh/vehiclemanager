package com.example.VehicleManagement.dto;

public class UserLoginRequest {

    private String userName;
    private String passWord;
    private String email;
    private String role;

    public UserLoginRequest() {
    }

    public UserLoginRequest(String userName, String passWord, String email, String role) {
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
