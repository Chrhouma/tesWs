package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.*;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.DeltaRepository;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.ServiceRecordRepository;
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
    @Autowired
    ServiceRecordRepository serviceRecordRepository;
    @Autowired
    ServiceRecordServices serviceRecordServices;
    @Autowired
    DeltaRepository deltaRepository;
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


    @Override
    public JsonArrayBuilder getInfoGlobalWebService(){
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder =Json.createObjectBuilder();


        List <WebService> webServiceList=(List<WebService>) webServicesRepository.findAll();
        List <ServiceRecord> serviceRecords= (List<ServiceRecord>) serviceRecordRepository.findAll();
        List <Delta> deltaList= (List<Delta>) deltaRepository.findAll();

        int nbWebServiceTotale=webServiceList.size();
        int nbServiceRecord= serviceRecords.size();
        int nbDelta= deltaList.size();
        jsonObjectBuilder.add("nbWebServiceTotale",nbWebServiceTotale);
        jsonObjectBuilder.add("nbServiceRecord",nbServiceRecord);
        jsonObjectBuilder.add("nbDelta",nbDelta);
        arrayBuilder.add(jsonObjectBuilder);
        return arrayBuilder;

    }
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
    public JsonArrayBuilder getWebserviceJson(String id) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder recordJsonArrayBuilder =Json.createArrayBuilder();
        JsonArrayBuilder scenarioServiceJsonArrayBuilder =Json.createArrayBuilder();
        List <WebService> webServiceList=(List<WebService>) webServicesRepository.findAll();
        List<ServiceRecord>serviceRecordList= (List<ServiceRecord>) serviceRecordRepository.findAll();

        for(WebService webService: webServiceList) {
            if ( webService.getId().equals(id)) {
               // List<ServiceRecord> serviceRecords = serviceRecordServices.getWebServiceRecordByWebServiceId(webService.getId());
                List<WebServiceScenario> webServiceScenarios= webService.getWebServicesScenario();
                System.out.println(webServiceScenarios.size());
                JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            JsonObjectBuilder jsonObjectBuilderschemaInput = Json.createObjectBuilder();
            JsonObjectBuilder jsonObjectBuilderschemaOutput = Json.createObjectBuilder();

            jsonObjectBuilder.add("id", webService.getId());
            jsonObjectBuilder.add("rang", webService.getRang());



            jsonObjectBuilder.add("name", webService.getName());
            jsonObjectBuilder.add("url", webService.getUrl());
            jsonObjectBuilder.add("description", webService.getDescription());
            jsonObjectBuilderschemaInput.add("id", webService.getInputSchema().getId());
            jsonObjectBuilderschemaOutput.add("id", webService.getOutSchema().getId());
            jsonObjectBuilder.add("InputShema", jsonObjectBuilderschemaInput);
            jsonObjectBuilder.add("OutputShema", jsonObjectBuilderschemaOutput);

            for(WebServiceScenario  webServiceScenario:webServiceScenarios){
                JsonObjectBuilder jsonObjectBuilder1 = Json.createObjectBuilder();
                jsonObjectBuilder1.add("name",webServiceScenario.getScenario().getName());
                jsonObjectBuilder1.add("id",webServiceScenario.getScenario().getId());
                scenarioServiceJsonArrayBuilder.add(jsonObjectBuilder1);
            }
                System.out.println("liste des scenarios"+ webService.getWebServicesScenario().get(0).getScenario().getName());

                for (ServiceRecord serviceRecord :serviceRecordList ) {

                    if(serviceRecord.getWebService().getId().equals(id)) {

                        JsonObjectBuilder jsonObjectBuilder1 = Json.createObjectBuilder();
                        jsonObjectBuilder1.add("id", serviceRecord.getId());
                        jsonObjectBuilder1.add("status", serviceRecord.getStatus());
                        jsonObjectBuilder1.add("name", "record  " + serviceRecord.getExecutionTime().toString());
                        jsonObjectBuilder1.add("date", serviceRecord.getDate().toString());
                        recordJsonArrayBuilder.add(jsonObjectBuilder1);

                    }
                }
                jsonObjectBuilder.add("scenarios",scenarioServiceJsonArrayBuilder);
                jsonObjectBuilder.add("records", recordJsonArrayBuilder);
                arrayBuilder.add(jsonObjectBuilder);
        }
        }
        return arrayBuilder;
    }

    @Override
    public void addWebService(WebService webService) {
        webServicesRepository.save(webService);
    }
}
