package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.WebService;

import javax.json.JsonArrayBuilder;
import java.io.IOException;


public interface WebServicesServices {
     JsonArrayBuilder getInfoGlobalWebService();
     WebService getWebService(String id);
     WebService getWebServiceByName(String name);
     JsonArrayBuilder getAllWebService();
     JsonArrayBuilder getWebserviceJson(String id);
     void addWebService(WebService webService);
     void deleteWebservice(String id);
     void testerWebservice(String id) throws IOException;
}
