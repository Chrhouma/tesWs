package com.pictimegroupe.FrontVendeur.testWebservice.services;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.pictimegroupe.FrontVendeur.testWebservice.*;

import com.pictimegroupe.FrontVendeur.testWebservice.services.Dates;
import com.pictimegroupe.FrontVendeur.testWebservice.services.ServiceRecordServices;

import io.restassured.RestAssured;
import io.restassured.config.SessionConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.ScenarioRepository;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
public class ScenarioServiceImpl implements ScenarioService {
    @Autowired
    ScenarioRepository scenarioRepository;
    @Autowired
    ScenarioService scenarioService;
    @Autowired
    WebServicesServices webServicesServices;
    @Autowired
    ServiceRecordServices serviceRecordServices;
    @Autowired
    ScenarioRecordService scenarioRecordService;
    TestWs testWs=new TestWs();

    private  String separateur="*****************************";
    private  String endWs="################################################################\n";
    private List <File> listscenaio2= new ArrayList<>();
    public  void writeOnFile(File f, String txt) throws IOException {
        FileWriter fw = new FileWriter(f,true);
        fw.write(txt);
        fw.close();
    }
    @Override
    public void tester(String idScenario,int rang, String idScenarioRecord,String idwebServcie) throws IOException{
        Dates date=new Dates();
        WebService webService=webServicesServices.getWebService(idwebServcie);
        String nameWs=webService.getName();
        RestAssured.baseURI = "http://127.0.0.1/";
        String url= webService.getUrl().substring(RestAssured.baseURI.length());
        System.out.println(url);
        String startTest=separateur+nameWs+separateur+"\n";
        String resultPath="/home/front-vendeur/Bureau/tesWs/webservice/"+nameWs+rang+date.datestr;
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
        System.out.println("je teste mon url            "+url);
        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,endWs);

        ServiceRecord serviceRecord =new ServiceRecord() ;
        String status="ok";
        serviceRecord.setWebService(webServicesServices.getWebServiceByName(nameWs));

        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(rang);

        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le service web "+ nameWs+"   marche bien");


    }

    @Override
    public  void testLogin(String idScenario,int rang,String idScenarioRecord) throws IOException {
        Dates date=new Dates();
        String startTest=separateur+"login"+separateur+"\n";
        RestAssured.baseURI = "http://127.0.0.1/";
        String resultPath="/home/front-vendeur/Bureau/tesWs/webservice/login"+rang+date.datestr;

        File resultFile = new File(resultPath);

        JsonObject json = new JsonObject();
        /*  json.addProperty("matricule","120393");
        json.addProperty("password","Soleil1!");*/
        RequestSpecification request = RestAssured.given();
        request.header("Content-type", "application/json");
        request
               /* .body(json.toString())*/
                .body("{\"matricule\":\"120393\",\"password\":\"Soleil1!\"}")
                .when().post("connexion/login").andReturn().sessionId();
        Response resp = request.post("connexion/login");

        //recupÃ©ration de la valeur de cookies dans une map
        Map<String,String> cookies=resp.getCookies();
        RestAssured.config = RestAssured.config().sessionConfig(new SessionConfig().sessionIdName("ID_SESSION"));
        RestAssured.sessionId=cookies.get("ID_SESSION");


        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,endWs);

        ServiceRecord serviceRecord =new ServiceRecord() ;

        String status="ok";

        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setWebService(webServicesServices.getWebServiceByName("login"));
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(rang);
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
       serviceRecordServices.addServiceRecord(serviceRecord);

        System.out.println("le login marcvhe bien ");
    }
    @Override
    public  void testRechercheClient(String idScenario,int rang,String idScenarioRecord)throws IOException{
        Dates date=new Dates();

        String startTest=separateur+"rech_client"+separateur+"\n";
        JsonObject json = new JsonObject();
        String resultPath=("/home/front-vendeur/Bureau/tesWs/webservice/client"+rang+date.datestr);
        File resultFile = new File(resultPath);
       /* json.addProperty("centrale", "0");
        json.addProperty("numeroClient", "P0126530");*/

        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
/*
                .body(json.toString())
*/
                .body("{\"centrale\":\"0\",\"numeroClient\":\"P0126530\"}")

                .sessionId(RestAssured.sessionId);

        Response  resp = request1.post("client/rech_client");

        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,endWs);

        ServiceRecord serviceRecord =new ServiceRecord() ;
        String status="ok";
        serviceRecord.setWebService(webServicesServices.getWebServiceByName("rechercheclient"));

        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(rang);

        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le recherche client marche bien ");

    }
    @Override
    public  void testProduitStock(String idScenario,int rang,String idScenarioRecord) throws IOException{
        Dates date=new Dates();

        String startTest=separateur+"stock_produits"+separateur+"\n";
        JsonObject json = new JsonObject();
        String resultPath=("/home/front-vendeur/Bureau/tesWs/webservice/stock"+rang + date.datestr);
        File resultFile = new File(resultPath);
        json.addProperty("references", "[\"0000100108\"]");
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                // .body(json.toString())
                .body("{\"references\": [\"1000000117\"]}")
                .sessionId(RestAssured.sessionId);
        Response  resp = request1.post("vente_panier/stock_produits/");

        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,endWs);

        ServiceRecord serviceRecord =new ServiceRecord() ;
        String status="ok";
        serviceRecord.setWebService(webServicesServices.getWebServiceByName("produitStock"));
        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(rang);
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));

        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le testProduitStock  marche bien ");

    }
    @Override
    public  void testAjoutProduit( String idScenario,int rang,String idScenarioRecord) throws IOException{
        Dates date=new Dates();

        String startTest=separateur+"ajout_produit"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/tesWs/webservice/addProduct" + rang+date.datestr );
        File resultFile = new File(resultPath);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .body("{\"eanPrincipal\": \"1000000117\", \"omnicanal\": false, \"produits\": [{\"numeroComposant\": 0}]}")
                .sessionId(RestAssured.sessionId);
        Response  resp = request1.post("vente_panier/ajout_produit");

        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,endWs);

        ServiceRecord serviceRecord =new ServiceRecord() ;
        String status="ok";
        serviceRecord.setWebService(webServicesServices.getWebServiceByName("ajoutProduit"));
        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(rang);
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le testAjout Produit  marche bien ");
    }
    @Override
    public  void  testParametrage(String idScenario,int rang,String idScenarioRecord) throws IOException {
        Dates date=new Dates();

        String startTest=separateur+"parametrage"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/tesWs/webservice/parametrage" +rang+ date.datestr );
        File resultFile = new File(resultPath);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .body("")
                .sessionId(RestAssured.sessionId);
        Response  resp = request1.get("configuration/parametrage");

        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,endWs);

        ServiceRecord serviceRecord =new ServiceRecord() ;

        String status="ok";
        serviceRecord.setWebService(webServicesServices.getWebServiceByName("parametrage"));
        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(rang);
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le prametrage marche bien ");
    }
    @Override
    public  void  testrafraichir(String idScenario ,int rang,String idScenarioRecord) throws IOException {
        Dates date=new Dates();

        String startTest=separateur+"rafraichir"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/tesWs/webservice/rafraichir" +rang+ date.datestr);
        File resultFile = new File(resultPath);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .body("")
                .sessionId(RestAssured.sessionId);
        Response  resp = request1.get("vente_panier/rafraichir");

        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,endWs);

        ServiceRecord serviceRecord =new ServiceRecord() ;

        String status="ok";
        serviceRecord.setWebService(webServicesServices.getWebServiceByName("rafraichir"));
        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(rang);
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le rafraichir marche bien");
    }
    @Override
    public  void  testvalider(String idScenario,int rang,String idScenarioRecord) throws IOException {
        Dates date=new Dates();

        String startTest=separateur+"valider"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/tesWs/webservice/valider" +rang+ date.datestr );
        File resultFile = new File(resultPath);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .body("")
                .sessionId(RestAssured.sessionId);
        Response  resp = request1.post("vente_panier/valider");

        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,endWs);

        ServiceRecord serviceRecord =new ServiceRecord() ;

        String status="ok";
        serviceRecord.setWebService(webServicesServices.getWebServiceByName("valider"));
        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(rang);
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le valider marche bien");
    }
    @Override
    public  void associerClient(String idScenario,int rang,String idScenarioRecord) throws IOException {
        Dates date=new Dates();

        String startTest = separateur + "associer_panier" + separateur + "\n";
        String resultPath = ("/home/front-vendeur/Bureau/tesWs/webservice/associerCLient" +rang+ date.datestr);
        File resultFile = new File(resultPath);
        RequestSpecification request1 = RestAssured.given().headers("Content-type", "application/json")
                .body("{\"numeroClient\": \"P0126530\"}")
                .sessionId(RestAssured.sessionId);
        Response resp = request1.post("client/associer_panier");

        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,endWs);

        ServiceRecord serviceRecord = new ServiceRecord();

        String status = "ok";
        serviceRecord.setWebService(webServicesServices.getWebServiceByName("associerClient"));
        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(rang);
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le assosier client marche bien");
    }
    @Override
    public  void testValiderModeLiv(String idScenario,int rang,String idScenarioRecord) throws IOException{
        Dates date=new Dates();

        String startTest=separateur+"valider_mode_liv"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/tesWs/webservice/validerModeLiv" +rang+ date.datestr );
        File resultFile = new File(resultPath);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .body("{ \"codeSiteReservation\": \"\",\"codeTransporteur\": \"LVB\",\"dateDelivrance\": 20391231,\"commandeEntrepot\": true, \"quantite\": 1, \"codeDelivrance\": \"L\",\"numeroLigne\": 1\n}")
                .sessionId(RestAssured.sessionId);
        Response  resp = request1.post("vente_panier/valider_mode_liv");

        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,endWs);

        ServiceRecord serviceRecord = new ServiceRecord();

        String status = "ok";
        serviceRecord.setWebService(webServicesServices.getWebServiceByName("ValiderModeLiv"));
        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(rang);
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("validerMode livraison client marche bien");
    }
    @Override
    public  void testPlaningLiv(String idScenario,int rang,String idScenarioRecord) throws IOException{
        Dates date=new Dates();

        String startTest=separateur+"planning_liv"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/tesWs/webservice/planingLiv" +rang+ date .datestr);
        File resultFile = new File(resultPath);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .body("{\"codeInsee\": \"59350\",\"codePays\": \"FRA\", \"codePostal\": \"59000\",\"numerosLignes\": [{\"ligne\": 1}]}")

                .sessionId(RestAssured.sessionId);
        Response  resp = request1.post("vente_panier/planning_liv");

        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,endWs);

        ServiceRecord serviceRecord = new ServiceRecord();

        String status = "ok";
        serviceRecord.setWebService(webServicesServices.getWebServiceByName("PlaningLiv"));
        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(rang);
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("palning livraison client marche bien");
    }
    @Override
    public   void testRechercheCp(String idScenario,int rang,String idScenarioRecord)throws IOException{
        Dates date=new Dates();

        String startTest=separateur+"rechercher_CP"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/tesWs/webservice/RechercheCp" +rang+ date.datestr);
        File resultFile = new File(resultPath);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .body("{\"codePays\": \"FRA\",\"codePostal\": \"62000\"}")
                .sessionId(RestAssured.sessionId);
        Response  resp = request1.post("client/rechercher_CP");

        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,endWs);

        ServiceRecord serviceRecord = new ServiceRecord();

        String status = "ok";
        serviceRecord.setWebService(webServicesServices.getWebServiceByName("rechercheCp"));

        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(rang);
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("recherche cp client marche bien");
    }
    @Override
    public  void  testvaliderVendeur(String idScenario,int rang,String idScenarioRecord) throws IOException {
        Dates date=new Dates();

        String startTest=separateur+"valider_vendeur_fin"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/tesWs/webservice/validerVendeur" +rang+ date.datestr );
        File resultFile = new File(resultPath);

        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .body("")
                .sessionId(RestAssured.sessionId);
        Response  resp = request1.post("vente_panier/valider_vendeur_fin");

        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,endWs);

        ServiceRecord serviceRecord = new ServiceRecord();

        String status = "ok";
        serviceRecord.setWebService(webServicesServices.getWebServiceByName("valider"));
        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(rang);
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("valider vendeur  marche bien");
    }
    @Override
    public  void  testDerniereCommande(String idScenario,int rang,String idScenarioRecord) throws IOException {
        Dates date=new Dates();

        String startTest=separateur+"derniere_commande"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/tesWs/webservice/Commande"+rang + date.datestr);
        File resultFile = new File(resultPath);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .body("")
                .sessionId(RestAssured.sessionId);
        Response  resp = request1.get("/vente_panier/derniere_commande");

        writeOnFile(resultFile,startTest);
        writeOnFile(resultFile,resp.asString());
        writeOnFile(resultFile,endWs);

        ServiceRecord serviceRecord = new ServiceRecord();

        String status = "ok";
        serviceRecord.setWebService(webServicesServices.getWebServiceByName("DerniereCommande"));
        serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        serviceRecord.setRang(rang);

        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("derniÃ¨re commande  marche bien");
    }

    @Override
    public Scenario getScenario(String id) {
        List<Scenario>listScenarios= scenarioRepository.findScenarioById(id);
        return listScenarios.get(0);
    }

    @Override
    public List<String> getWebServiceNamesByIdScenario(String id) {
        List<String> webservicesNames= new LinkedList<>();
        for (WebServiceScenario webServiceScenario:this.getScenario(id).getWebServicesScenario()){
            webservicesNames.add(webServiceScenario.getWebService().getName());
        }
        return webservicesNames;
    }

    @Override
    public List<Integer> getWebServiceRangByIdScenario(String id) {
        List<Integer> webServiceRang=new LinkedList<>();
         for(WebServiceScenario webServiceScenario:this.getScenario(id).getWebServicesScenario()){
             webServiceRang.add(webServiceScenario.getRang());
        }
        return webServiceRang;
    }

    @Override
    public List<String> getWebServiceNamesByRang(String id,int rang) {
        List<Integer> rangList=this.getWebServiceRangByIdScenario(id);
        List<String> nameWs=new LinkedList<>();
        for (WebServiceScenario webServiceScenario:this.getScenario(id).getWebServicesScenario()){
            if(webServiceScenario.getRang()==rang) {
                nameWs.add(webServiceScenario.getWebService().getName());
            }
        }
        return nameWs;
    }

    @Override
    public List<String> getWebServiceIdByRang(String id,int rang) {
       List<String> ListIdWebService=new LinkedList<>();
        for (WebServiceScenario webServiceScenario:this.getScenario(id).getWebServicesScenario()){
            if(webServiceScenario.getRang()==rang) {
                ListIdWebService.add(webServiceScenario.getWebService().getId());
            }
        }
        return ListIdWebService;
    }


    @Override
    public JsonArrayBuilder getAllScenario() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        JsonArrayBuilder recordJsonArrayBuilder =Json.createArrayBuilder();
        List<Scenario> scenarioList=(List<Scenario>)scenarioRepository.findAll();
        for(Scenario scenario :scenarioList){
            List<ScenarioRecord> scenarioRecords= scenarioRecordService.getScenarioRecordByScenario(scenario.getId());
            JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();
            jsonObjectBuilder.add("id",scenario.getId());
            jsonObjectBuilder.add("name",scenario.getName());
            jsonObjectBuilder.add("cron",scenario.getCron());
            jsonObjectBuilder.add("webServices",scenario.getScenarioJson().build().getJsonArray("webServices"));
            for(ScenarioRecord scenarioRecord :scenarioRecords){
                JsonObjectBuilder jsonObjectBuilder1=Json.createObjectBuilder();
                jsonObjectBuilder1.add("id",scenarioRecord.getId());
                recordJsonArrayBuilder.add(jsonObjectBuilder1);
            }
            jsonObjectBuilder.add("Records",recordJsonArrayBuilder);
            arrayBuilder.add(jsonObjectBuilder);
        }
        return arrayBuilder;
    }


 @Override
 public JsonArrayBuilder getScenarioJson(String id) {

     JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
     JsonArrayBuilder recordJsonArrayBuilder =Json.createArrayBuilder();
     List<Scenario> scenarioList=(List<Scenario>)scenarioRepository.findAll();
     for(Scenario scenario :scenarioList) {
         JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
         if (scenario.getId().equals(id)) {
             List<ScenarioRecord> scenarioRecords = scenarioRecordService.getScenarioRecordByScenario(scenario.getId());
             jsonObjectBuilder.add("id", scenario.getId());
             jsonObjectBuilder.add("name", scenario.getName());
             jsonObjectBuilder.add("cron", scenario.getCron());
             jsonObjectBuilder.add("webServices", scenario.getScenarioJson().build().getJsonArray("webServices"));
             for (ScenarioRecord scenarioRecord : scenarioRecords) {
                 JsonObjectBuilder jsonObjectBuilder1 = Json.createObjectBuilder();
                 jsonObjectBuilder1.add("id", scenarioRecord.getId());
                 jsonObjectBuilder1.add("status",scenarioRecord.getStatus());
                 jsonObjectBuilder1.add("name","record  "+scenarioRecord.getExecutionTime().toString());
                 recordJsonArrayBuilder.add(jsonObjectBuilder1);
             }
             jsonObjectBuilder.add("records", recordJsonArrayBuilder);
             arrayBuilder.add(jsonObjectBuilder);
         }
     }
     return arrayBuilder;

 }

    @Override
    public Scenario AddScenario(Scenario scenario) {


        return scenarioRepository.save(scenario);
    }




}
