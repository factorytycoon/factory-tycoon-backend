package com.factory.tycoon.email.dto;

import java.time.LocalDateTime;

public class EmailAuthRecord {
    private String email;
    private int authNum;
    private LocalDateTime createdAt;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAuthNum() { return authNum; }
    public void setAuthNum(int authNum) { this.authNum = authNum; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
