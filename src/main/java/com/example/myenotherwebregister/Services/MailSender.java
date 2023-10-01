package com.example.myenotherwebregister.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {
    @Autowired
    private JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String username;
    public void sendEmail(String userEmail, String confirmationLink,String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(userEmail);
        mailMessage.setText(message);
        mailMessage.setSubject("Подтверждение адреса электронной почты");
        mailMessage.setText("Для подтверждения адреса электронной почты перейдите по ссылке: " + confirmationLink);
        emailSender.send(mailMessage);
    }
}
