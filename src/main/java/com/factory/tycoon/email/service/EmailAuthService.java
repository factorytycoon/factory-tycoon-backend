package com.factory.tycoon.email.service;

import com.factory.tycoon.email.dto.EmailAuthRecord;
import com.factory.tycoon.email.repository.EmailAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailAuthService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private EmailAuthRepository emailAuthRepository;

    @Value("${spring.mail.username:}")
    private String mailUsername;

    @Autowired
    private TemplateEngine templateEngine;

    private static final long EXPIRE_MINUTES = 5; // 인증 코드 만료 시간

    public void sendAuthCode(String toEmail) throws MessagingException, UnsupportedEncodingException {
        // 코드 생성 및 발급 (중복 생성시 덮어씀)
        int authNum = generate4Digits();
        emailAuthRepository.upsertAuthCode(toEmail, authNum, LocalDateTime.now());

        // 메일 전송
        String title = "[Factory Tycoon] 인증 코드는 " + authNum + "입니다";
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");

        helper.setTo(toEmail);
        helper.setSubject(title);

        if (mailUsername != null && !mailUsername.isBlank()) {
            helper.setFrom(mailUsername);
        }

        // Use Thymeleaf to process the mail.html template
        Context context = new Context();
        context.setVariable("code", authNum);
        String htmlContent = templateEngine.process("mail", context);

        helper.setText(htmlContent, true);
        emailSender.send(message);
    }

    public String verifyCode(String email, int inputCode) {
        EmailAuthRecord record;
        try {
            record = emailAuthRepository.findByEmail(email);
        } catch (Exception e) {
            return "Error: no auth code";
        }

        if (inputCode != record.getAuthNum()) {
            return "Error: not correct auth code";
        }

        Duration duration = Duration.between(record.getCreatedAt(), LocalDateTime.now());
        emailAuthRepository.deleteByEmail(email); // 성공 만료 상관없이 삭제

        if (duration.toMinutes() < EXPIRE_MINUTES) {
            return "Success";
        }
        return "Error: over 5 minute";
    }

    public void deleteExistCode(String email) {
        emailAuthRepository.deleteByEmail(email); // 없으면 그냥 0
    }

    private int generate4Digits() {
        return new Random().nextInt(9000) + 1000;
    }
}
