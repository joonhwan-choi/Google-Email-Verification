package com.study.GoogleEmail.api.controller;

import com.study.GoogleEmail.api.dto.EmailDto;
import com.study.GoogleEmail.api.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;

    // 인증코드 메일 발송
    @CrossOrigin(origins = "http://localhost:3000") // React 앱의 주소
    @PostMapping("/send")
    public String mailSend(@RequestBody EmailDto emailDto) throws MessagingException {
        System.out.println("EmailController.mailSend()"); // System.out.println 사용
        emailService.sendEmail(emailDto.getMail());
        return "인증코드가 발송되었습니다.";
    }

    // 인증코드 인증
    @CrossOrigin(origins = "http://localhost:3000") // React 앱의 주소
    @PostMapping("/verify")
    public String verify(@RequestBody EmailDto emailDto) {
        System.out.println("EmailController.verify()"); // 로그 출력
        boolean isVerify = emailService.verifyEmailCode(emailDto.getMail(), emailDto.getVerifyCode());

        // 인증 코드 비교 후 로그 추가
        if (isVerify) {
            System.out.println("인증 코드 일치: " + emailDto.getVerifyCode());
        } else {
            System.out.println("인증 코드 불일치: 입력된 코드 " + emailDto.getVerifyCode());
        }

        return isVerify ? "인증이 완료되었습니다." : "인증 실패하셨습니다.";
    }
}
