package com.study.GoogleEmail.api.controller;

import com.study.GoogleEmail.api.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import jakarta.mail.MessagingException;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final EmailService emailService;

    // 이메일 인증 요청 페이지를 보여주는 GET 메서드
    @GetMapping("/email/sendPage")
    public String showSendEmailPage() {
        return "emailSend";  // 이메일 전송을 위한 페이지 (emailSend.html)
    }

    // 이메일 인증 확인 페이지를 보여주는 GET 메서드
    @GetMapping("/email/verifyPage")
    public String showVerifyEmailPage(@RequestParam String email, Model model) {
        model.addAttribute("email", email);  // 이메일을 뷰로 전달
        return "emailVerify";  // 인증 확인 페이지
    }

    // 이메일 인증 요청을 처리하는 POST 메서드
    @GetMapping("/email/send")
    public String sendEmail(@RequestParam String mail) throws MessagingException {
        emailService.sendEmail(mail);  // 이메일 전송
        return "redirect:/email/verifyPage?email=" + mail;  // 인증 페이지로 리다이렉트
    }

    // 이메일 인증 코드를 확인하는 POST 메서드
    @GetMapping("/email/verify")
    public String verifyEmail(@RequestParam String mail, @RequestParam String verifyCode, Model model) {
        boolean isVerified = emailService.verifyEmailCode(mail, verifyCode);
        if (isVerified) {
            model.addAttribute("message", "인증 성공");
        } else {
            model.addAttribute("message", "인증 실패");
        }
        return "emailResult";  // 인증 결과 페이지 (emailResult.html)
    }
}
