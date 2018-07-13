package com.pictimegroupe.FrontVendeur.testWebservice.services;

import org.springframework.mail.javamail.JavaMailSender;

public interface SendEmailService {
    public void NotificationService(JavaMailSender javaMailSender);
}

