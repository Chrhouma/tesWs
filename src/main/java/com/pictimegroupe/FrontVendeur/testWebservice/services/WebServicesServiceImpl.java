package com.pictimegroupe.FrontVendeur.testWebservice.services;


import com.pictimegroupe.FrontVendeur.testWebservice.*;
import com.pictimegroupe.FrontVendeur.testWebservice.Util.Const;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.DeltaRepository;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.ServiceRecordRepository;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.WebServicesRepository;
import io.restassured.RestAssured;
import io.restassured.config.SessionConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public  void writeOnFile(File f, String txt) throws IOException {
        FileWriter fw = new FileWriter(f,true);
        fw.write(txt);
        fw.close();
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

            jsonObjectBuilder.add("id",webService.getId());
            jsonObjectBuilder.add("rang",webService.getRang());
            jsonObjectBuilder.add("name",webService.getName());
            jsonObjectBuilder.add("url",webService.getUrl());
            jsonObjectBuilder.add("body",webService.getBody());
            jsonObjectBuilder.add ("methode",webService.getMethod());
            jsonObjectBuilder.add("description",webService.getDescription());

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
                List<WebServiceScenario> webServiceScenarios= webService.getWebServicesScenario();
                JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("id", webService.getId());
            jsonObjectBuilder.add("rang", webService.getRang());
            jsonObjectBuilder.add("name", webService.getName());
            jsonObjectBuilder.add("url", webService.getUrl());
            jsonObjectBuilder.add("description", webService.getDescription());
            jsonObjectBuilder.add("body",webService.getBody());
            jsonObjectBuilder.add("methode",webService.getMethod());

            for(WebServiceScenario  webServiceScenario:webServiceScenarios){
                JsonObjectBuilder jsonObjectBuilder1 = Json.createObjectBuilder();
                jsonObjectBuilder1.add("name",webServiceScenario.getScenario().getName());
                jsonObjectBuilder1.add("id",webServiceScenario.getScenario().getId());
                scenarioServiceJsonArrayBuilder.add(jsonObjectBuilder1);
            }
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
    @Override
    public void deleteWebservice(String id) {
        webServicesRepository.deleteById(id);
        System.out.println("web service suprimé");
    }


     /*cette methode doit etre exécutée pour récupérer la valeur de la session */

    public  void getSession() {

        RestAssured.baseURI = Const.BASEURL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-type", "application/json");
        request.body("{\"matricule\":\"120393\",\"password\":\"Soleil1!\"}")
                .when().post("connexion/login").andReturn().sessionId();
        Response resp = request.post("connexion/login");

        //recupÃ©ration de la valeur de cookies dans une map
        Map<String,String> cookies=resp.getCookies();
        RestAssured.config = RestAssured.config().sessionConfig(new SessionConfig().sessionIdName("ID_SESSION"));
        RestAssured.sessionId=cookies.get("ID_SESSION");

        System.out.println("la valeur de la session est "+ RestAssured.sessionId.toString());
    }



    @Override
    public void testerWebservice(String idwebServcie) throws IOException {
        //appel à la methode qui permet de recupérer la session
        getSession();

        Dates date=new Dates();
        WebService webService=getWebService(idwebServcie);
        String nameWs=webService.getName();
        RestAssured.baseURI = Const.BASEURL;
        String url= webService.getUrl().substring(RestAssured.baseURI.length());


        String startTest=Const.separateur+nameWs+Const.separateur+"\n";
        String resultPath="src/main/resources/webservice/"+nameWs+date.datestr;
        File resultFile = new File(resultPath);
        RequestSpecification request = RestAssured.given();
        request.header("Content-type", "application/json");
        request
                .body(webService.getBody())
                .sessionId(RestAssured.sessionId);
        Response resp;

        if(webService.getMethod().equals("Post")) {
            resp = request.post(url);
        }
        else {
            resp = request.get(url);
        }

        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,"\n");
        writeOnFile(resultFile,Const.endWs);

        ServiceRecord serviceRecord =new ServiceRecord() ;
        String status="ok";
        serviceRecord.setWebService(getWebServiceByName(nameWs));

        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(0);
        serviceRecord.setScenarioRecord(null);
    //  serviceRecord.setScenarioRecord(getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le service web "+ nameWs+"  est tester unitairement");

    }
}
