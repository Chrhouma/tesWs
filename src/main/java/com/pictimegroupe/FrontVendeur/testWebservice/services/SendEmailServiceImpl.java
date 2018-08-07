package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.Delta;
import com.pictimegroupe.FrontVendeur.testWebservice.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailServiceImpl implements  SendEmailService {
    @Autowired
    public JavaMailSender javaMailSender;

    @Autowired
    public void NotificationService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }


    public void sendDelta() {

     System.out.println("je commence l'envoi du mail");
        SimpleMailMessage mail= new SimpleMailMessage();

        mail.setTo("gabaroski@gmail.com");
        mail.setFrom("gabaroski@gmail.com");
        mail.setSubject("test web service");
        mail.setText( "Notre application a détectée un changement au niveau du scénario  ");
        try {
            javaMailSender.send(mail);
        }
        catch (Exception e){
            System.out.println("exception  "+e);
        }



        System.out.println("envoi terminé");

    }

}
