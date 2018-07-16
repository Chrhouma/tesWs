package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.Scenario;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

public interface SendEmailService {
    public void NotificationService(JavaMailSender javaMailSender);
    public void sendDelta() throws MailException;
}

