package com.pictimegroupe.FrontVendeur.testWebservice.controller;


import com.pictimegroupe.FrontVendeur.testWebservice.*;
import com.pictimegroupe.FrontVendeur.testWebservice.Exception.GestionRoleException;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.DeltaRepository;
import com.pictimegroupe.FrontVendeur.testWebservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * la class qui permet la navigation
 */
@RestController
public class ApiController {
    @Autowired
    UserServices userServices;
    @Autowired
    WebServicesServices webServicesServices;
    @Autowired
    ScenarioService scenarioService;
    @Autowired
    ScenarioRecordService scenarioRecordService;
    @Autowired
    ServiceRecordServices serviceRecordServices;
    @Autowired
    DeltaServices deltaServices;
    @Autowired
    DeltaRepository deltaRepository;
    @Autowired
    SendEmailService sendEmailService;
   private String baseURI = "http://127.0.0.1/";
    /**
     * @param login
     * @param password
     * @return
     * @throws GestionRoleException
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/user/connect", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String connect(@RequestParam(value = "login", required = false) String login,
                          @RequestParam(value = "password", required = false) String password) throws GestionRoleException {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        if (login != null && password != null) {
           obj.add("user",userServices.connect(login, password));

        }
        return obj.build().toString();
    }

    /**
     * Cette methode de faire l'ajout d'un user par un autrex
     *
     * @param login
     * @param password
     * @param rolename
     * @param roleRight
     * @return
     * @throws GestionRoleException
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/user/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addUser(@RequestParam(value = "login", required = false) String login,
                          @RequestParam(value = "password", required = false) String password,
                          @RequestParam(value = "rolename", required = false) String rolename,
                          @RequestParam(value = "roleright", required = false) String roleright) throws GestionRoleException {

        JsonObjectBuilder obj = Json.createObjectBuilder();
        if (login != null && password != null) {
            User user = new User();
            Role role = new Role();

            role.setName(rolename);
            role.setRights(roleright);

            user.setLogin(login);
            user.setPassword(password);
            user.setRole(role);

            userServices.addUser(user);
            obj.add("user", user.getUserJson());
        }
        return obj.build().toString();
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value="/scenario/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )

    public String  saveScenario(@RequestParam(value = "name")String name,
                                @RequestParam(value= "cron")String cron,
                                @RequestBody List<WebServiceScenario> webServiceScenario)
    {
        Scenario scenario= new Scenario();
        scenario.setName(name);
        scenario.setCron(cron);
        List <WebServiceScenario> webServiceScenarios= new LinkedList<>();

        for(int i =0; i<webServiceScenario.size();i++){
            WebServiceScenario webServiceScenario1= new WebServiceScenario();
            webServiceScenario1.setScenario(scenario);
            webServiceScenario1.setWebService(webServicesServices.getWebService(webServiceScenario.get(i).getWebService().getId()));
            webServiceScenario1.setRang(webServiceScenario.get(i).getRang());
            webServiceScenarios.add(webServiceScenario1);
        }
        scenario.setWebServicesScenario(webServiceScenarios);
        scenarioService.AddScenario(scenario);
        JsonObjectBuilder obj = Json.createObjectBuilder();
        obj.add("Scenario",scenarioService.getAllScenario());

        return obj.build().toString();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/webService/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

    public String addWebService(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "url", required = false) String url,
                                @RequestParam(value = "description", required = false) String description,
                                @RequestParam(value = "methode", required = false) String methode,
                               // @RequestParam(value = "body", required = false) String body,

                                @RequestParam(value = "inputSchemaName", required = false) String inputSchemaName,
                                @RequestParam(value = "inputSchemapath", required = false) String inputSchemapath,
                                @RequestParam(value = "outputSchemaName", required = false) String outputSchemaName,
                                @RequestParam(value = "outputSchemapath", required = false) String outputSchemapath,
                                @RequestBody  String body)  {


        JsonObjectBuilder obj = Json.createObjectBuilder();
        WebService webService = new WebService();
        Schema inputSchema = new Schema();
        Schema outputSchema = new Schema();
        inputSchema.setName(inputSchemaName);
        inputSchema.setShemapath(inputSchemapath);
        outputSchema.setName(outputSchemaName);
        outputSchema.setShemapath(outputSchemapath);

        webService.setName(name);
        webService.setRang(0);
        webService.setUrl(baseURI+url);
        webService.setMethod(methode);
        webService.setBody(body);
        webService.setDescription(description);

       /* webService.setInputSchema(inputSchema);
        webService.setOutSchema(outputSchema);*/


        webServicesServices.addWebService(webService);
        obj.add("webservice", webService.getWebServiceJson());

        return obj.build().toString();
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/webServices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllWebServices() {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        obj.add("webservices", webServicesServices.getAllWebService());
        return obj.build().toString();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/webServices/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getInfoGenerale() {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        obj.add("infogenerale", webServicesServices.getInfoGlobalWebService());
        return obj.build().toString();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/webService", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findOneWebService(@RequestParam(value = "id", required = false) String id) {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        obj.add("webService",webServicesServices.getWebserviceJson(id));
        return obj.build().toString();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/scenarios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllScenarios() {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        obj.add("nbScenario",scenarioService.getNbScenario());
        obj.add("scenarios", scenarioService.getAllScenario());
        return obj.build().toString();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/scenario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findOneScenario(@RequestParam(value = "id", required = false) String id) {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        obj.add("scenario",scenarioService.getScenarioJson(id));
        return obj.build().toString();
    }


    public WebService createWs(String id,int  rang){
        WebService webService = new WebService();
        webService=webServicesServices.getWebService(id);
        webService.setRang(rang);
        return webService;
    }



    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/scenario/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addScenario(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "corn", required = false) String corn) {

        List<WebServiceScenario> arrayWebserviceScenario = new ArrayList<WebServiceScenario>();
        JsonObjectBuilder obj = Json.createObjectBuilder();
        Scenario scenario = new Scenario();
        scenario.setName(name);
        scenario.setCron(corn);
           arrayWebserviceScenario.add(new WebServiceScenario(webServicesServices.getWebService("1c018f8b-5eaa-4743-b615-3b08b0124b0c"),scenario,10));
           arrayWebserviceScenario.add(new WebServiceScenario(webServicesServices.getWebService("87c713a0-fc75-493b-a7dc-99e5efdbcd1e") , scenario, 20));
           arrayWebserviceScenario.add(new WebServiceScenario(webServicesServices.getWebService("1c018f8b-5eaa-4743-b615-3b08b0124b0c"),scenario,30));
              scenario.setWebServicesScenario(arrayWebserviceScenario);
        scenario.setWebServicesScenario(arrayWebserviceScenario);
               return obj.build().toString();
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "scenario/tester",method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String testerScenario(@RequestParam(value = "idScenario")String idScenario) throws IOException {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        scenarioRecordService.testerScenarioAut(idScenario);
        //scenarioRecordService.testerScenario(idScenario);
        System.out.println("je teste mon ws");
        return obj.build().toString();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "scenarioRecord/comparer",method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String comparerScenario(@RequestParam(value = "idScenarioRecord1")String idScenario1,
                                   @RequestParam(value = "idScenarioRecord2")String idScenario2) throws IOException {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        scenarioRecordService.comparerScenario(idScenario1,idScenario2);
        obj.add("deltas",  deltaServices.getAllDeltaByIdeScenarioRcord(idScenario1));
        return obj.build().toString();
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "serviceRecord/comparer",method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String comparerWebservice(@RequestParam(value = "idServiceRecord1")String idServiceRecord1,
                                     @RequestParam(value = "idServiceRecord2")String idServiceRecord2)throws  IOException{
       JsonObjectBuilder obj = Json.createObjectBuilder();
        serviceRecordServices.comparerWebservice(idServiceRecord1,idServiceRecord2);
        obj.add("deltas", deltaServices.getAllDeltaByIdServiceRecord(idServiceRecord1));
        return obj.build().toString();
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "home", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String compareAlone() throws IOException {
         return scenarioRecordService.compareAutomatic();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "serviceRecord/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addScenarioRecord(@RequestParam(value = "executionTime", required = false) Date executionTime,
                                    @RequestParam(value = "date", required = false) Date date,
                                    @RequestParam(value = "webService", required = false) String webServiceId,
                                    @RequestParam(value = "scenarioRecord", required = false) ScenarioRecord scenarioRecord,
                                    @RequestParam(value = "resultPath", required = false) String resultPath,
                                    @RequestParam(value = "status", required = false) String status) {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        ServiceRecord serviceRecord = new ServiceRecord();
        serviceRecord.setExecutionTime(executionTime);
        serviceRecord.setDate(date);
        serviceRecord.setWebService(webServicesServices.getWebService(webServiceId));
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecordServices.addServiceRecord(serviceRecord);
        obj.add("serviceRecord", serviceRecord.getServiceRecordJson());
        return obj.build().toString();
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "scenarioRecord/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addServiceRecord(@RequestParam(value = "executionTime", required = false) Date executionTime,
                                    @RequestParam(value = "date", required = false) Date date,
                                    @RequestParam(value = "scenarioId", required = false) String scenarioId,
                                    @RequestParam(value = "status", required = false) String status) {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        ScenarioRecord scenarioRecord = new ScenarioRecord();
        scenarioRecord.setExecutionTime(executionTime);
        scenarioRecord.setDate(date);
        scenarioRecord.setStatus(status);
        scenarioRecord.setScenario(scenarioService.getScenario(scenarioId));
        scenarioRecordService.addScenarioRecord(scenarioRecord);
        obj.add("scenarioRecord", scenarioRecord.getSenarioRecordJson());
        return obj.build().toString();
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/scenarioRecords", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllScenarioRecord() {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        obj.add("ScenarioRecords", scenarioRecordService.getAllScenarioRecord());
        return obj.build().toString();
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/serviceRecord", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllServiceRecord() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("serviceRecord", serviceRecordServices.getAllServiceRecord());
        return jsonObjectBuilder.build().toString();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "delta", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public  String findAllDeltabyIdserviceRecord(@RequestParam(value = "idServicerecord", required = false) String idServicerecord){
        JsonObjectBuilder jsonObjectBuilder =Json.createObjectBuilder();
       // JsonArrayBuilder jsonArrayBuilder =Json.createArrayBuilder();
        jsonObjectBuilder.add("deltas",deltaServices.getDeltaByIdServiceRecord(idServicerecord));
      // jsonArrayBuilder.add(jsonObjectBuilder);
        return jsonObjectBuilder.build().toString();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "delta/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public  String addDelta(@RequestParam(value = "expectedValue", required = false) String expectedValue,
                            @RequestParam(value = "node", required = false) String node,
                            @RequestParam(value = "registeedValue", required = false) String registeedValue,
                            @RequestParam(value = "serviceRecordId", required = false) String serviceRecordId){
        JsonObjectBuilder jsonObjectBuilder=Json.createObjectBuilder();
        Delta delta=new Delta();
        delta.setExpctedValue(expectedValue);
        delta.setNode(node);
        delta.setRegisteedValue(registeedValue);
        delta.setServiceRecord(serviceRecordServices.getServiceRecord(serviceRecordId));

        deltaServices.addDelta(delta);
        jsonObjectBuilder.add("delta",delta.getDeltoJson());
        return jsonObjectBuilder.build().toString();
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value="/sendEmail")
    public void send(){
        sendEmailService.sendDelta();

    }

}
