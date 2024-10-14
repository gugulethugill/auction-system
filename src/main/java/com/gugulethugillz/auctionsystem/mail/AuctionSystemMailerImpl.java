package com.gugulethugillz.auctionsystem.mail;

import com.gugulethugillz.auctionsystem.mail.AuctionSystemMailer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionSystemMailerImpl implements AuctionSystemMailer {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String subject, String message, String emailAddress) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }

    void sendEmailWithAttachment(String subject, String message, String emailAddress) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(emailAddress);
        helper.setSubject(subject);
        helper.setText(message);
        helper.addAttachment("bike.png", new ClassPathResource("bike.png"));
        javaMailSender.send(msg);
    }
}
