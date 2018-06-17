package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.google.gson.JsonObject;

import com.pictimegroupe.FrontVendeur.testWebservice.ServiceRecord;

import com.pictimegroupe.FrontVendeur.testWebservice.services.Dates;
import com.pictimegroupe.FrontVendeur.testWebservice.services.ServiceRecordServices;

import io.restassured.RestAssured;
import io.restassured.config.SessionConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TestWs {


     private Dates date=new Dates();
     private  String separateur="*****************************";
     private  String endWs="################################################################\n";
     private List <File> listscenaio2= new ArrayList<>();



    public TestWs() {
    }
    public  void writeOnFile(File f, String txt) throws IOException {
        FileWriter fw = new FileWriter(f,true);
        fw.write(txt);
        fw.close();
    }
    public  void testLogin() {
        String startTest=separateur+"login"+separateur+"\n";
        RestAssured.baseURI = "http://127.0.0.1/";
       // String resultPath="/home/front-vendeur/Bureau/FrontVendeur.testWebservice/tesWs/login"+date.date1;

        //File resultFile = new File(resultPath);

        JsonObject json = new JsonObject();
        json.addProperty("matricule","120393");
        json.addProperty("password","Soleil1!");
        RequestSpecification request = RestAssured.given();
        request.header("Content-type", "application/json");
        request.body(json.toString()).when().post("connexion/login").andReturn().sessionId();
        Response resp = request.post("connexion/login");

        //recupération de la valeur de cookies dans une map
        Map<String,String> cookies=resp.getCookies();
        RestAssured.config = RestAssured.config().sessionConfig(new SessionConfig().sessionIdName("ID_SESSION"));
        RestAssured.sessionId=cookies.get("ID_SESSION");

      //  writeOnFile(resultFile,startTest);
        //writeOnFile(resultFile,resp.asString());
       // writeOnFile(resultFile,endWs);
        ServiceRecord serviceRecord =new ServiceRecord() ;
        ServiceRecordServices serviceRecordServices= new ServiceRecordServicesImpl();

        String status="ok";

       /* serviceRecord.setExecutionTime(date.executionTime);
        serviceRecord.setDate(date.actuelle);
        //serviceRecord.setWebService(webServicesServices.getWebServiceByName("login"));
        //serviceRecord.setScenarioRecord(scenarioRecord);
        serviceRecord.setResultPath(resultPath);*/
       serviceRecord.setStatus(status);
        System.out.println(serviceRecord.getStatus());

        serviceRecordServices.addServiceRecord(serviceRecord);

        System.out.println("le login marcvhe bien ");
            }

    public  void testRechercheClient(File Ftoatale)throws IOException{
        String startTest=separateur+"rech_client"+separateur+"\n";
        JsonObject json = new JsonObject();
        File f1 = new File("/home/front-vendeur/IdeaProjects/Autotest/testWs/WsSearchClient/client" +date);
        FileWriter fw1 = new FileWriter(f1);
        json.addProperty("centrale", "0");
        json.addProperty("numeroClient", "P0126530");
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .body(json.toString())
                .sessionId(RestAssured.sessionId);
        Response  resp1 = request1.post("client/rech_client");
        fw1.write(resp1.asString());
        fw1.close();
        writeOnFile(Ftoatale,startTest);
        writeOnFile(Ftoatale,resp1.asString());
        writeOnFile(Ftoatale,endWs);
    }

    public  void testProduitStock(File Ftoatale) throws IOException{
        String startTest=separateur+"stock_produits"+separateur+"\n";
        JsonObject json = new JsonObject();
        File f2 = new File("/home/front-vendeur/IdeaProjects/Autotest/testWs/WsStock/stock" + date);
        FileWriter fw2 = new FileWriter(f2);
        json.addProperty("references", "[\"0000100108\"]");
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
               // .body(json.toString())
                .body("{\"references\": [\"1000000117\"]}")
                .sessionId(RestAssured.sessionId);
        Response  resp1 = request1.post("vente_panier/stock_produits/");
        fw2.write(resp1.asString());
        fw2.close();
        writeOnFile(Ftoatale,startTest);
        writeOnFile(Ftoatale,resp1.asString());
        writeOnFile(Ftoatale,endWs);
    }

  public  void testAjoutProduit(File Ftoatale) throws IOException{
      String startTest=separateur+"ajout_produit"+separateur+"\n";
      File f = new File("/home/front-vendeur/IdeaProjects/Autotest/testWs/WsAjoutProduit/addProduct" + date );
      FileWriter fw = new FileWriter(f);
      RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
        .body("{\"eanPrincipal\": \"1000000117\", \"omnicanal\": false, \"produits\": [{\"numeroComposant\": 0}]}")
        .sessionId(RestAssured.sessionId);
      Response  resp1 = request1.post("vente_panier/ajout_produit");
      fw.write(resp1.asString());
      fw.close();
      writeOnFile(Ftoatale,startTest);
      writeOnFile(Ftoatale,resp1.asString());
      writeOnFile(Ftoatale,endWs);
  }
    public  void  testParametrage(File Ftoatale) throws IOException {
        String startTest=separateur+"parametrage"+separateur+"\n";
        File f = new File("/home/front-vendeur/IdeaProjects/Autotest/testWs/parametrage/parametrage" + date );
        FileWriter fw = new FileWriter(f);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .sessionId(RestAssured.sessionId);
        Response  resp1 = request1.get("configuration/parametrage");
        fw.write(resp1.asString());
        fw.close();
        writeOnFile(Ftoatale,startTest);
        writeOnFile(Ftoatale,resp1.asString());
        writeOnFile(Ftoatale,endWs);
    }
    public  void  testrafraichir(File Ftoatale) throws IOException {
        String startTest=separateur+"rafraichir"+separateur+"\n";
        File f = new File("/home/front-vendeur/IdeaProjects/Autotest/testWs/rafraichir/rafraichir" + date);
        FileWriter fw = new FileWriter(f);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .sessionId(RestAssured.sessionId);
        Response  resp1 = request1.get("vente_panier/rafraichir");
        fw.write(resp1.asString());
        fw.close();
        writeOnFile(Ftoatale,startTest);
        writeOnFile(Ftoatale,resp1.asString());
        writeOnFile(Ftoatale,endWs);
    }

    public  void  testvalider(File Ftoatale) throws IOException {
        String startTest=separateur+"valider"+separateur+"\n";
        File f = new File("/home/front-vendeur/IdeaProjects/Autotest/testWs/valider/valider" + date );
        FileWriter fw = new FileWriter(f);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .sessionId(RestAssured.sessionId);
        Response  resp1 = request1.post("vente_panier/valider");
        fw.write(resp1.asString());
        fw.close();
        writeOnFile(Ftoatale,startTest);
        writeOnFile(Ftoatale,resp1.asString());
        writeOnFile(Ftoatale,endWs);
    }

    public  void associerClient(File Ftoatale) throws IOException{
        String startTest=separateur+"associer_panier"+separateur+"\n";
        File f = new File("/home/front-vendeur/IdeaProjects/Autotest/testWs/associerCLient/associerCLient" + date );
        FileWriter fw = new FileWriter(f);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .body("{\"numeroClient\": \"P0126530\"}")
                .sessionId(RestAssured.sessionId);
        Response  resp1 = request1.post("client/associer_panier");
        fw.write(resp1.asString());
        fw.close();
        writeOnFile(Ftoatale,startTest);
        writeOnFile(Ftoatale,resp1.asString());
        writeOnFile(Ftoatale,endWs);
    }
    public  void testValiderModeLiv(File Ftoatale) throws IOException{
        String startTest=separateur+"valider_mode_liv"+separateur+"\n";
        File f = new File("/home/front-vendeur/IdeaProjects/Autotest/testWs/validerModeLiv/validerModeLiv" + date );
        FileWriter fw = new FileWriter(f);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
         .body("{ \"codeSiteReservation\": \"\",\"codeTransporteur\": \"LVB\",\"dateDelivrance\": 20391231,\"commandeEntrepot\": true, \"quantite\": 1, \"codeDelivrance\": \"L\",\"numeroLigne\": 1\n}")
          .sessionId(RestAssured.sessionId);
        Response  resp1 = request1.post("vente_panier/valider_mode_liv");
        fw.write(resp1.asString());
        fw.close();
        writeOnFile(Ftoatale,startTest);
        writeOnFile(Ftoatale,resp1.asString());
        writeOnFile(Ftoatale,endWs);
    }
    public  void testPlaningLiv(File Ftoatale) throws IOException{
        String startTest=separateur+"planning_liv"+separateur+"\n";
        File f = new File("/home/front-vendeur/IdeaProjects/Autotest/testWs/planingLiv/planingLiv" + date );
        FileWriter fw = new FileWriter(f);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .body("{\"codeInsee\": \"59350\",\"codePays\": \"FRA\", \"codePostal\": \"59000\",\"numerosLignes\": [{\"ligne\": 1}]}")

                .sessionId(RestAssured.sessionId);
        Response  resp1 = request1.post("vente_panier/planning_liv");
        fw.write(resp1.asString());
        fw.close();
        writeOnFile(Ftoatale,startTest);
        writeOnFile(Ftoatale,resp1.asString());
        writeOnFile(Ftoatale,endWs);
    }
    public   void testRechercheCp(File Ftoatale)throws IOException{
        String startTest=separateur+"rechercher_CP"+separateur+"\n";
        File f = new File("/home/front-vendeur/IdeaProjects/Autotest/testWs/RechercheCp/RechercheCp" + date );
        FileWriter fw = new FileWriter(f);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
          .body("{\"codePays\": \"FRA\",\"codePostal\": \"62000\"}")
           .sessionId(RestAssured.sessionId);
        Response  resp1 = request1.post("client/rechercher_CP");
        fw.write(resp1.asString());
        fw.close();
        writeOnFile(Ftoatale,startTest);
        writeOnFile(Ftoatale,resp1.asString());
        writeOnFile(Ftoatale,endWs);
    }
    public  void  testvaliderVendeur(File Ftoatale) throws IOException {
        String startTest=separateur+"valider_vendeur_fin"+separateur+"\n";
        File f = new File("/home/front-vendeur/IdeaProjects/Autotest/testWs/validerVendeur/validerVendeur" + date );
        FileWriter fw = new FileWriter(f);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .sessionId(RestAssured.sessionId);
        Response  resp1 = request1.post("vente_panier/valider_vendeur_fin");
        fw.write(resp1.asString());
        fw.close();
        writeOnFile(Ftoatale,startTest);
        writeOnFile(Ftoatale,resp1.asString());
        writeOnFile(Ftoatale,endWs);
    }
    public  void  testDerniereCommande(File Ftoatale) throws IOException {
        String startTest=separateur+"derniere_commande"+separateur+"\n";
        File f = new File("/home/front-vendeur/IdeaProjects/Autotest/testWs/deniereCommande/Commande" + date );
        FileWriter fw = new FileWriter(f);
        RequestSpecification request1= RestAssured.given().headers("Content-type", "application/json")
                .sessionId(RestAssured.sessionId);
        Response  resp1 = request1.get("/vente_panier/derniere_commande");
        fw.write(resp1.asString());
        fw.close();
        writeOnFile(Ftoatale,startTest);
        writeOnFile(Ftoatale,resp1.asString());
        writeOnFile(Ftoatale,endWs);
    }
// Scénario livraison emportée
    public void scenario1(File f) throws IOException {
        testLogin();
        testRechercheClient(f);
        testProduitStock(f);
        testAjoutProduit(f);
        associerClient(f);
        testrafraichir(f);
        associerClient(f);
        testrafraichir(f);
        testvalider(f);
        testParametrage(f);
        testvalider(f);
        testrafraichir(f);
        testvaliderVendeur(f);
        testDerniereCommande(f);

    }

   // scénario livraison boulanger avec commande entrepôt
    public void scenario2(File f) throws IOException{
        testLogin();
        testProduitStock(f);
        testRechercheClient(f);
        associerClient(f);
        testrafraichir(f);
        testAjoutProduit(f);
        testValiderModeLiv(f);
        testrafraichir(f);
        testvalider(f);
        testPlaningLiv(f);
        testrafraichir(f);
        testRechercheCp(f);
        testvaliderVendeur(f);
        testrafraichir(f);
        testDerniereCommande(f);
        listscenaio2.add(f);
        System.out.println(listscenaio2.size());

    }

    public void testerFonction(){
        System.out.println("je testeste lelogin si ca marche ou pas");
    }
}
