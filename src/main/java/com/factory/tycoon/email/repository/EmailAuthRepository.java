package com.factory.tycoon.email.repository;

import com.factory.tycoon.email.dto.EmailAuthRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public class EmailAuthRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // email기준으로 upsert
    public void upsertAuthCode(String email, int authNum, LocalDateTime createdAt) {
        String sql = """
            INSERT INTO emailauth (email, auth_num, created_at)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE
              auth_num = VALUES(auth_num),
              created_at = VALUES(created_at)
        """;
        jdbcTemplate.update(sql, email, authNum, Timestamp.valueOf(createdAt));
    }

    // email기준으로 조회
    public EmailAuthRecord findByEmail(String email) {
        String sql = "SELECT email, auth_num, created_at FROM emailauth WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> {
            EmailAuthRecord dto = new EmailAuthRecord();
            dto.setEmail(rs.getString("email"));
            dto.setAuthNum(rs.getInt("auth_num"));
            dto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return dto;
        });
    }

    // email기준으로 삭제
    public void deleteByEmail(String email) {
        String sql = "DELETE FROM emailauth WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }
}
