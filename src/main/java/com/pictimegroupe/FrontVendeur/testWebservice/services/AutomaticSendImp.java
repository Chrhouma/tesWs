package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.Util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AutomaticSendImp implements AutomaticSend {
@Autowired
SendEmailService  sendEmailService;

       @Override
       // pour commencer le test automatique suprimer le commentaire pour la ligne au dessous
      //  @Scheduled(fixedRate = Const.NBMS)
    public void testAutomatic() {
       System.out.println("Automatic test");
       sendEmailService.sendDelta();

    }

}
