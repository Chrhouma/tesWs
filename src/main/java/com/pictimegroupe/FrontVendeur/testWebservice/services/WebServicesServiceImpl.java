package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.WebService;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.WebServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.List;

@Service
public class WebServicesServiceImpl implements WebServicesServices {
    @Autowired
    WebServicesRepository webServicesRepository;

    public WebServicesServiceImpl() {
    }


    @Override
    public WebService getWebService(String id) {
        List<WebService>listws= webServicesRepository.findWebServiceById(id);
        return listws.get(0);
    }

    @Override
    public WebService getWebServiceByName(String name) {
        List<WebService> webServiceList=webServicesRepository.findWebServiceByName(name);

        return webServiceList.get(0);
    }
  /*  @Override
    public WebService getWebServiceByName(String name) {
        List <WebService> webServiceList=(List<WebService>) webServicesRepository.findAll();
        WebService webService1=new WebService();

        for(WebService webService: webServiceList){
            if (webService.getName().equals(name)) {
                webService1 = webService;
            }
        }
        return webService1;
    }*/


    @Override
    public JsonArrayBuilder getAllWebService() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        List <WebService> webServiceList=(List<WebService>) webServicesRepository.findAll();
        for(WebService webService: webServiceList){

            JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();
            JsonObjectBuilder jsonObjectBuilderschemaInput= Json.createObjectBuilder();
            JsonObjectBuilder jsonObjectBuilderschemaOutput= Json.createObjectBuilder();

            jsonObjectBuilder.add("id",webService.getId());
          jsonObjectBuilder.add("rang",webService.getRang());

            jsonObjectBuilder.add("name",webService.getName());
            jsonObjectBuilder.add("url",webService.getUrl());
            jsonObjectBuilder.add("description",webService.getDescription());
            jsonObjectBuilderschemaInput.add("id",webService.getInputSchema().getId());
             jsonObjectBuilderschemaOutput.add("id",webService.getOutSchema().getId());
             jsonObjectBuilder.add("InputShema",jsonObjectBuilderschemaInput);
            jsonObjectBuilder.add("OutputShema",jsonObjectBuilderschemaOutput);

            arrayBuilder.add(jsonObjectBuilder);
        }
        return arrayBuilder;
    }

    @Override
    public void addWebService(WebService webService) {
        webServicesRepository.save(webService);
    }
}
