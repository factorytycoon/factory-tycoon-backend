package com.factory.tycoon.email.ctrl;

import com.factory.tycoon.email.dto.SendAuthCodeRequest;
import com.factory.tycoon.email.dto.VerifyAuthCodeRequest;
import com.factory.tycoon.email.service.EmailAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ft/email")
public class EmailAuthController {

    @Autowired
    private EmailAuthService emailAuthService;

    @PostMapping("/send-authcode")
    public ResponseEntity<?> sendAuthCode(@RequestBody SendAuthCodeRequest req)
            throws MessagingException, UnsupportedEncodingException {

        emailAuthService.sendAuthCode(req.getEmail()); // 코드 생성 및 메일 전송

        // JSON 형식으로 응답 반환
        return ResponseEntity.ok(Map.of("message", "Success"));
    }

    @PostMapping("/check-authcode")
    public ResponseEntity<?> checkAuthCode(@RequestBody VerifyAuthCodeRequest req) {
        String result = emailAuthService.verifyCode(req.getEmail(), req.getAuthNum());

        // JSON 형식으로 응답 반환
        return ResponseEntity.ok(Map.of("message", result));
    }
}
