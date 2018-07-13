package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.Delta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailServiceImpl implements  SendEmailService {
    private JavaMailSender javaMailSender;

    @Autowired
    public void NotificationService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    public void sendDelta(Delta delta) throws MailException {

        SimpleMailMessage mail= new SimpleMailMessage();
        mail.setTo("charfeddine91@hotmail.fr");
        mail.setFrom("gabaroski@gmail.com");
        mail.setSubject("test web service");
        mail.setText( "la noeud affectée est"+delta.getNode());
        javaMailSender.send(mail);

    }

}
