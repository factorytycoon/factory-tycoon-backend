package com.factory.tycoon.user.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_status")
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Boolean status;

    public UserStatus() {}

    public UserStatus(UserEntity user, LocalDate date, Boolean status) {
        this.user = user;
        this.date = date;
        this.status = (status == null) ? false : status;
    }

    public Long getId() { return id; }
    public UserEntity getUser() { return user; }
    public LocalDate getDate() { return date; }
    public Boolean getStatus() { return status; }

    public void setUser(UserEntity user) { this.user = user; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setStatus(Boolean status) { this.status = status; }
}
