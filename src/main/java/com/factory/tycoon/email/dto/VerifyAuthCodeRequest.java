package com.factory.tycoon.email.dto;

public class VerifyAuthCodeRequest {
    private String email;
    private int authNum;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAuthNum() { return authNum; }
    public void setAuthNum(int authNum) { this.authNum = authNum; }
}
