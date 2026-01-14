package com.factory.tycoon.email.ctrl;

import com.factory.tycoon.email.dto.SendAuthCodeRequest;
import com.factory.tycoon.email.dto.VerifyAuthCodeRequest;
import com.factory.tycoon.email.service.EmailAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/email")
public class EmailAuthController {

    @Autowired
    private EmailAuthService emailAuthService;

    @PostMapping("/send-authcode")
    public ResponseEntity<?> sendAuthCode(@RequestBody SendAuthCodeRequest req)
            throws MessagingException, UnsupportedEncodingException {

        //emailAuthService.deleteExistCode(req.getEmail());

        emailAuthService.sendAuthCode(req.getEmail()); //코드 생성 및 메일 전송

        return ResponseEntity.ok("Success");
    }

    @PostMapping("/check-authcode")
    public ResponseEntity<?> checkAuthCode(@RequestBody VerifyAuthCodeRequest req) {
        String result = emailAuthService.verifyCode(req.getEmail(), req.getAuthNum());
        return ResponseEntity.ok(result);
    }
}
