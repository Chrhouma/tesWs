package com.pictimegroupe.FrontVendeur.testWebservice.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.pictimegroupe.FrontVendeur.testWebservice.*;
import com.pictimegroupe.FrontVendeur.testWebservice.Exception.GestionRoleException;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.DeltaRepository;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.ScenarioRepository;
import com.pictimegroupe.FrontVendeur.testWebservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
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
   private String baseURI = "http://127.0.0.1/";
    /**
     * @param login
     * @param password
     * @return
     * @throws GestionRoleException
     */
    @RequestMapping(value = "/user/connect", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String connect(@RequestParam(value = "login", required = false) String login,
                          @RequestParam(value = "password", required = false) String password) throws GestionRoleException {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        if (login != null && password != null) {
            userServices.connect(login, password);

        }
        return obj.build().toString();
    }

    /**
     * Cette methode de faire l'ajout d'un user par un autre
     *
     * @param login
     * @param password
     * @param rolename
     * @param roleRight
     * @return
     * @throws GestionRoleException
     */
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

    @RequestMapping(value = "/webService/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addWebService(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "url", required = false) String url,
                                @RequestParam(value = "description", required = false) String description,
                                @RequestParam(value = "inputSchemaName", required = false) String inputSchemaName,
                                @RequestParam(value = "inputSchemapath", required = false) String inputSchemapath,
                                @RequestParam(value = "outputSchemaName", required = false) String outputSchemaName,
                                @RequestParam(value = "outputSchemapath", required = false) String outputSchemapath) {

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
        webService.setDescription(description);
        webService.setInputSchema(inputSchema);
        webService.setOutSchema(outputSchema);


        webServicesServices.addWebService(webService);
        obj.add("webservice", webService.getWebServiceJson());

        return obj.build().toString();
    }

    @RequestMapping(value = "/webServices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllWebServices() {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        obj.add("webservices", webServicesServices.getAllWebService());
        return obj.build().toString();
    }

    @RequestMapping(value = "/scenarios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllScenarios() {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        obj.add("scenarios", scenarioService.getAllScenario());
        return obj.build().toString();
    }

    public WebService createWs(String id,int  rang){
        WebService webService = new WebService();
          webService=webServicesServices.getWebService(id);
        webService.setRang(rang);
        return webService;
    }

    @RequestMapping(value = "/scenario/add/scenario1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Scenario addScenario(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "corn", required = false) String corn) {

        Scenario scenario = new Scenario();
        scenario.setName(name);
        scenario.setCron(corn);
        scenario=scenarioService.AddScenario(scenario);
        System.out.println("====>"+scenario.getId()+"<====");

        JsonObjectBuilder obj = Json.createObjectBuilder();
        List<WebServiceScenario> arrayWebserviceScenario = new ArrayList<WebServiceScenario>();


        //login
      //  WebService webService;
        //webService=webServicesServices.getWebService("c21c5721-35ef-417f-84a3-12ed58fff494");
       // webService.setRang(01);
        arrayWebserviceScenario.add(new WebServiceScenario(new WebService("1c018f8b-5eaa-4743-b615-3b08b0124b0c") , scenario, 1));
        System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk1");

        //recherche client
       // arrayWebservice.add(webServicesServices.getWebService("70b24665-f282-4607-9ed4-d4b6fb296a26"));
        arrayWebserviceScenario.add(new WebServiceScenario(new WebService("04af2446-14f8-4125-90a5-997d6b4dd646") , scenario, 2));
        System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk2");
/*        //produit stock
       // arrayWebservice.add(webServicesServices.getWebService("3713f47c-9722-4b08-98bb-a5384d6f8758"));
       arrayWebservice.add(createWs("89e52ad0-6c89-4a40-b21c-a1c97c9ba391",3));
        System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk3");

        //ajout produit
//        arrayWebservice.add(webServicesServices.getWebService("3e7e16a6-2e48-4d66-b6a5-056b2ff6c0ce"));
        arrayWebservice.add(createWs("87d664b1-03c8-4996-9004-4b12ce93b29f",4));
        System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk4");

        //associer client
//        arrayWebservice.add(webServicesServices.getWebService("64a6b694-d7da-4189-a383-5ec851639bc5"));
        arrayWebservice.add(createWs("edbef8c2-3488-427b-acbc-c8ff0aa04542",5));
        System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk5");
        //rafraichir
  //      arrayWebservice.add(webServicesServices.getWebService("46b765f7-96e9-4a50-8ba6-99a0051f6a2d"));
        arrayWebservice.add(createWs("c15e34c3-6256-4681-9b39-5afe252f0b8a",6));
        System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk6");
        //associer Client
//        arrayWebservice.add(webServicesServices.getWebService("64a6b694-d7da-4189-a383-5ec851639bc5"));
        arrayWebservice.add(createWs("edbef8c2-3488-427b-acbc-c8ff0aa04542",7));
        System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk7");
        //rafraichir
       // arrayWebservice.add(webServicesServices.getWebService("46b765f7-96e9-4a50-8ba6-99a0051f6a2d"));
        arrayWebservice.add(createWs("c15e34c3-6256-4681-9b39-5afe252f0b8a",8));
        System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk8");
        //valider
        //arrayWebservice.add(webServicesServices.getWebService("f4f02399-3ea8-40cf-9459-66796874c259"));
        arrayWebservice.add(createWs("21eba1d9-c4a8-4146-955f-0d9126bf5d95",9));
        System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk9");
        //parametrage
        //arrayWebservice.add(webServicesServices.getWebService("a6bf2676-3a55-40e2-a498-13e93d8e6125"));
        arrayWebservice.add(createWs("12788c43-7c73-447f-b853-8cebfeb6bf72",10));
        System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk10");
        // valider
        //arrayWebservice.add(webServicesServices.getWebService("f4f02399-3ea8-40cf-9459-66796874c259"));
        arrayWebservice.add(createWs("21eba1d9-c4a8-4146-955f-0d9126bf5d95",11));
        System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk11");
        //rafraichir
        //arrayWebservice.add(webServicesServices.getWebService("46b765f7-96e9-4a50-8ba6-99a0051f6a2d"));
        arrayWebservice.add(createWs("c15e34c3-6256-4681-9b39-5afe252f0b8a",12));
        System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk12");
        //valider Vendeur

        //arrayWebservice.add(webServicesServices.getWebService("a470cda4-8119-4874-875f-37392890c84c"));
        arrayWebservice.add(createWs("f81b6826-a1f6-4434-a6e7-01a5cf1d871d",13));
        System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk13");
        //derniere commande
        //arrayWebservice.add(webServicesServices.getWebService("ba268027-e5f2-4bf7-99b6-52a74f64b173"));
        arrayWebservice.add(createWs("61b521aa-9d18-429b-aa6c-7400b1ed2ba6",14));
        System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk14");*/

        /*Scenario scenario = new Scenario();
        scenario.setName(name);
        scenario.setCron(corn);*/

      /*for(WebService webService :arrayWebservice) {
        System.out.println(webService.getRang());
          //System.out.println(webService.getId());
        //  scenario.setRang(webService.getRang());
        //  scenario.setRang(webService.getRang());
          webService.setRang(1);
       //   scenarioService.AddScenario(scenario);
      }*/

      //scenarioService.AddScenario(scenario);
//        obj.add("scenarios", scenario.getScenarioJson());
//        System.out.println("je rajoute avec succées");
        scenario.setWebServicesScenario(arrayWebserviceScenario);
        scenarioService.AddScenario(scenario);

        return null;
    }
    /*@RequestMapping(value = "/scenario/add/scenario2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addScenario2(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "corn", required = false) String corn) {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        List<WebService> arrayWebservice = new LinkedList();

        //login
        arrayWebservice.add(webServicesServices.getWebService("c21c5721-35ef-417f-84a3-12ed58fff494"));
        //produit stock
        arrayWebservice.add(webServicesServices.getWebService("3713f47c-9722-4b08-98bb-a5384d6f8758"));
        //recherche client
        arrayWebservice.add(webServicesServices.getWebService("70b24665-f282-4607-9ed4-d4b6fb296a26"));
        //associer client
        arrayWebservice.add(webServicesServices.getWebService("64a6b694-d7da-4189-a383-5ec851639bc5"));
        //rafraichir
        arrayWebservice.add(webServicesServices.getWebService("46b765f7-96e9-4a50-8ba6-99a0051f6a2d"));
        //ajout produit
        arrayWebservice.add(webServicesServices.getWebService("3e7e16a6-2e48-4d66-b6a5-056b2ff6c0ce"));
        //valider mode livraison
        arrayWebservice.add(webServicesServices.getWebService("53cb67b4-c760-422d-8249-d3194f48905e"));
        //rafraichir
        arrayWebservice.add(webServicesServices.getWebService("46b765f7-96e9-4a50-8ba6-99a0051f6a2d"));
        //valider
        arrayWebservice.add(webServicesServices.getWebService("f4f02399-3ea8-40cf-9459-66796874c259"));
        //planing livraison
        arrayWebservice.add(webServicesServices.getWebService("3af860db-576d-49a8-9cd4-af9e2ecceb71"));
        //rafraichir
        arrayWebservice.add(webServicesServices.getWebService("46b765f7-96e9-4a50-8ba6-99a0051f6a2d"));
        //rechercheCP
        arrayWebservice.add(webServicesServices.getWebService("ab55a647-e55f-48fe-9f9a-c6ba5c863447"));
        //valider Vendeur
        arrayWebservice.add(webServicesServices.getWebService("a470cda4-8119-4874-875f-37392890c84c"));
        //rafraichir
        arrayWebservice.add(webServicesServices.getWebService("46b765f7-96e9-4a50-8ba6-99a0051f6a2d"));
        //derniere commande
        arrayWebservice.add(webServicesServices.getWebService("ba268027-e5f2-4bf7-99b6-52a74f64b173"));

        Scenario scenario = new Scenario();
        scenario.setName(name);
        scenario.setCron(corn);
        scenario.setWebServices(arrayWebservice);

        scenarioService.AddScenario(scenario);

        obj.add("scenarios", scenario.getScenarioJson());

        return obj.build().toString();
    }*/
    @RequestMapping(value = "scenario/tester",method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void testerScenario(@RequestParam(value = "idScenario")String idScenario) throws IOException {

    scenarioRecordService.testerScenario(idScenario);
    System.out.println("je teste mon ws");
    }

    @RequestMapping(value = "scenarioRecord/comparer",method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void comparerScenario(@RequestParam(value = "idScenarioRecord1")String idScenario1,
                                 @RequestParam(value = "idScenarioRecord2")String idScenario2) throws IOException {

        scenarioRecordService.comparerScenario(idScenario1,idScenario2);
        System.out.println("je compar mes scenarioRecord");
    }


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
        //serviceRecord.setScenarioRecord(scenarioRecord);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecordServices.addServiceRecord(serviceRecord);
        obj.add("serviceRecord", serviceRecord.getServiceRecordJson());
        return obj.build().toString();
    }
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

    @RequestMapping(value = "/scenarioRecord", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllScenarioRecord() {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        obj.add("ScenarioRecords", scenarioRecordService.getAllScenarioRecord());
        return obj.build().toString();
    }

    @RequestMapping(value = "/serviceRecord", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllServiceRecord() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("serviceRecord", serviceRecordServices.getAllServiceRecord());
        return jsonObjectBuilder.build().toString();
    }

    @RequestMapping(value = "delta", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public  String findAllDeltabyIdserviceRecord(@RequestParam(value = "idServicerecord", required = false) String idServicerecord){
        JsonObjectBuilder jsonObjectBuilder =Json.createObjectBuilder();
       // JsonArrayBuilder jsonArrayBuilder =Json.createArrayBuilder();
        jsonObjectBuilder.add("deltas",deltaServices.getDeltaByIdServiceRecord(idServicerecord));
      // jsonArrayBuilder.add(jsonObjectBuilder);
        return jsonObjectBuilder.build().toString();
    }
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

    @RequestMapping(value = "testing", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public  void findlisteServicerecordByScenario(@RequestParam(value = "scenario", required = false) String scenario){

       serviceRecordServices.getAllServiceRecordByScenario(scenario);
    }
}
