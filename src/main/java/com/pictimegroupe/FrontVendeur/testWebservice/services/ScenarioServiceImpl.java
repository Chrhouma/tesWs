package com.pictimegroupe.FrontVendeur.testWebservice.services;
import com.google.gson.JsonObject;

import com.pictimegroupe.FrontVendeur.testWebservice.ServiceRecord;

import com.pictimegroupe.FrontVendeur.testWebservice.services.Dates;
import com.pictimegroupe.FrontVendeur.testWebservice.services.ServiceRecordServices;

import io.restassured.RestAssured;
import io.restassured.config.SessionConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.pictimegroupe.FrontVendeur.testWebservice.Scenario;
import com.pictimegroupe.FrontVendeur.testWebservice.WebService;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ScenarioServiceImpl implements ScenarioService {
    @Autowired
    ScenarioRepository scenarioRepository;
    ScenarioService scenarioService;
    @Autowired
    WebServicesServices webServicesServices;
    @Autowired
    ServiceRecordServices serviceRecordServices;
    @Autowired
    ScenarioRecordService scenarioRecordService;
    TestWs testWs=new TestWs();
    private Dates date=new Dates();
    private  String separateur="*****************************";
    private  String endWs="################################################################\n";
    private List <File> listscenaio2= new ArrayList<>();
    public  void writeOnFile(File f, String txt) throws IOException {
        FileWriter fw = new FileWriter(f,true);
        fw.write(txt);
        fw.close();
    }
    @Override
    public  void testLogin(String idScenarioRecord) throws IOException {
        String startTest=separateur+"login"+separateur+"\n";
        RestAssured.baseURI = "http://127.0.0.1/";
        String resultPath="/home/front-vendeur/Bureau/FrontVendeur.testWebservice/tesWs/login"+date.date1;

        File resultFile = new File(resultPath);

        JsonObject json = new JsonObject();
        json.addProperty("matricule","120393");
        json.addProperty("password","Soleil1!");
        RequestSpecification request = RestAssured.given();
        request.header("Content-type", "application/json");
        request.body(json.toString()).when().post("connexion/login").andReturn().sessionId();
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

       serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecord.setResultPath(resultPath);
        serviceRecord.setStatus(status);
        System.out.println(serviceRecord.getStatus());

       serviceRecordServices.addServiceRecord(serviceRecord);

        System.out.println("le login marcvhe bien ");
    }
    @Override
    public  void testRechercheClient(String idScenarioRecord)throws IOException{
        String startTest=separateur+"rech_client"+separateur+"\n";
        JsonObject json = new JsonObject();
        String resultPath=("/home/front-vendeur/Bureau/FrontVendeur.testWebservice/tesWs/client" +date.date1);
        File resultFile = new File(resultPath);
        json.addProperty("centrale", "0");
        json.addProperty("numeroClient", "P0126530");
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .body(json.toString())
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
       serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le recherche client marche bien ");

    }
    @Override
    public  void testProduitStock(String idScenarioRecord) throws IOException{
        String startTest=separateur+"stock_produits"+separateur+"\n";
        JsonObject json = new JsonObject();
        String resultPath=("/home/front-vendeur/Bureau/FrontVendeur.testWebservice/tesWs/stock" + date.date1);
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
       serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));

        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le testProduitStock  marche bien ");

    }
    @Override
    public  void testAjoutProduit( String idScenarioRecord) throws IOException{
        String startTest=separateur+"ajout_produit"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/FrontVendeur.testWebservice/tesWs/addProduct" + date.date1 );
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
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le testAjout Produit  marche bien ");
    }
    @Override
    public  void  testParametrage(String idScenarioRecord) throws IOException {
        String startTest=separateur+"parametrage"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/FrontVendeur.testWebservice/tesWs/parametrage" + date.date1 );
        File resultFile = new File(resultPath);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
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
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le prametrage marche bien ");
    }
    @Override
    public  void  testrafraichir(String idScenarioRecord) throws IOException {
        String startTest=separateur+"rafraichir"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/FrontVendeur.testWebservice/tesWs/rafraichir" + date.date1);
        File resultFile = new File(resultPath);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
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
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le rafraichir marche bien");
    }
    @Override
    public  void  testvalider(String idScenarioRecord) throws IOException {
        String startTest=separateur+"valider"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/FrontVendeur.testWebservice/tesWs/valider" + date.date1 );
        File resultFile = new File(resultPath);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
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
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le valider marche bien");
    }
    @Override
    public  void associerClient(String idScenarioRecord) throws IOException {
        String startTest = separateur + "associer_panier" + separateur + "\n";
        String resultPath = ("/home/front-vendeur/Bureau/FrontVendeur.testWebservice/tesWs/associerCLient" + date.date1);
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
       serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("le assosier client marche bien");
    }
    @Override
    public  void testValiderModeLiv(String idScenarioRecord) throws IOException{
        String startTest=separateur+"valider_mode_liv"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/FrontVendeur.testWebservice/tesWs/validerModeLiv" + date.date1 );
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
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("validerMode livraison client marche bien");
    }
    @Override
    public  void testPlaningLiv(String idScenarioRecord) throws IOException{
        String startTest=separateur+"planning_liv"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/FrontVendeur.testWebservice/tesWs/planingLiv" + date .date1);
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
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("palning livraison client marche bien");
    }
    @Override
    public   void testRechercheCp(String idScenarioRecord)throws IOException{
        String startTest=separateur+"rechercher_CP"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/FrontVendeur.testWebservice/tesWs/RechercheCp" + date.date1);
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
       serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("recherche cp client marche bien");
    }
    @Override
    public  void  testvaliderVendeur(String idScenarioRecord) throws IOException {
        String startTest=separateur+"valider_vendeur_fin"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/FrontVendeur.testWebservice/tesWs/validerVendeur" + date.date1 );
        File resultFile = new File(resultPath);

        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
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
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("valider vendeur  marche bien");
    }
    @Override
    public  void  testDerniereCommande(String idScenarioRecord) throws IOException {
        String startTest=separateur+"derniere_commande"+separateur+"\n";
        String resultPath=("/home/front-vendeur/Bureau/FrontVendeur.testWebservice/tesWs/Commande" + date.date1);
        File resultFile = new File(resultPath);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
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
        serviceRecord.setScenarioRecord(scenarioRecordService.getScenarioRecord(idScenarioRecord));
        serviceRecordServices.addServiceRecord(serviceRecord);
        System.out.println("derniÃ¨re commande  marche bien");
    }


    @Override
    public Scenario getScenario(String id) {
        List<Scenario>listScenarios= scenarioRepository.findAllById(id);
        return listScenarios.get(0);
    }

    @Override
    public List<String> getWebServiceNamesByIdScenario(String id) {
        List<String> webservicesNames= new LinkedList<>();
        for(WebService webService:this.getScenario(id).getWebServices()){
            webservicesNames.add(webService.getName());
        }
        return webservicesNames;
    }


    @Override

    public JsonArrayBuilder getAllScenario() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        List<Scenario> scenarioList=(List<Scenario>)scenarioRepository.findAll();
        for(Scenario scenario :scenarioList){
            JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();

            jsonObjectBuilder.add("id",scenario.getId());
            jsonObjectBuilder.add("name",scenario.getName());
            jsonObjectBuilder.add("cron",scenario.getCron());
            jsonObjectBuilder.add("webServices",scenario.getScenarioJson().build().getJsonArray("webServices"));

            arrayBuilder.add(jsonObjectBuilder);
        }
        return arrayBuilder;
    }

    @Override
    public void AddScenario(Scenario scenario) {
        scenarioRepository.save(scenario);
    }



}
