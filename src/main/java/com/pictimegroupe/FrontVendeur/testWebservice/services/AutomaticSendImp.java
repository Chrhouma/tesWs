package com.pictimegroupe.FrontVendeur.testWebservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AutomaticSendImp implements AutomaticSend {
@Autowired
SendEmailService  sendEmailService;

     // public static final int nbmS = 86400000;
    //   @Override
   // @Scheduled(fixedRate = nbmS)
    public void testAutomatic() {
       // System.out.println("Automatic test");
        //  sendEmailService.sendDelta();

    }

}
