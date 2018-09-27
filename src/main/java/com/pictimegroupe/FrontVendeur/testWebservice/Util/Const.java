package com.pictimegroupe.FrontVendeur.testWebservice.Util;

public class Const {
    // taux de modification pour envoyer un email
    public static final int TAUXMODIFICATION=5;
    public static  final String BASEURL ="http://127.0.0.1/";
    // sépareteur des web services
    public  static final String separateur="*****************************";
    // séparateur pour détecter la fin d'un web service
    public static final String endWs="################################################################\n";
    // adresse mail du destintaire
    public static final String ADRESSEDESTINATAIRE = "destinatiare@gmail.com";
    // adresse mail d'émetteur
    public static final String ADRESSEEMETTEUR = "emetteur@gmail.com";
    // par défaut le test automatique s'éxecute chaque 86400000 millisecondes (24 heures)
    public static final int NBMS = 86400000;
}
