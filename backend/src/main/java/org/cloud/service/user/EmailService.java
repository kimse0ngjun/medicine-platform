package org.cloud.service.user;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendPasswordResetMail(
            String toEmail,
            String resetLink
    ) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(toEmail);

        message.setSubject("비밀번호 재설정");

        message.setText(
                "아래 링크를 클릭하세요.\n\n"
                + resetLink
        );

        mailSender.send(message);
    }
}
