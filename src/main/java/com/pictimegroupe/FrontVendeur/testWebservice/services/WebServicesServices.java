package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.WebService;

import javax.json.JsonArrayBuilder;


public interface WebServicesServices {
     WebService getWebService(String id);
     WebService getWebServiceByName(String name);
     JsonArrayBuilder getAllWebService();
     void addWebService(WebService webService);
}
