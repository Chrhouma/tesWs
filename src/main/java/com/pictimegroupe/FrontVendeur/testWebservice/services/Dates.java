package com.pictimegroupe.FrontVendeur.testWebservice.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dates {
    public Dates() {
    }
     Date actuelle = new Date();
     DateFormat dateFormat1 = new SimpleDateFormat( "yyyy-MM-dd-hh:MM");
     DateFormat dateFormat2 = new SimpleDateFormat( "yyyy-MM-dd");
     String date1 = dateFormat1.format(actuelle.getTime());
     String date2 = dateFormat2.format(actuelle.getTime());
     Date  executionTime=actuelle;
     public Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
    public  String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(yesterday());
    }
}