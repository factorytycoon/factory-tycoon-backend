package com.factory.tycoon.user.domain.dto;

import java.time.LocalDate;

public class UserStatusDto {
    private Long id;
    private Long userId;
    private LocalDate date;
    private Boolean status;

    public UserStatusDto() {}
    public UserStatusDto(Long id, Long userId, LocalDate date, Boolean status) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.status = status;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
}
