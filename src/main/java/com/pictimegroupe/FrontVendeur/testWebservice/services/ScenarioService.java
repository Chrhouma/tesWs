package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.Scenario;
import com.pictimegroupe.FrontVendeur.testWebservice.WebService;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.util.List;

public interface ScenarioService  {

    Scenario getScenario(String id);
    List<String> getWebServiceNamesByIdScenario(String id);

    List <Integer> getWebServiceRangByIdScenario(String id);
    List<String> getWebServiceNamesByRang(String id,int rang);
    JsonArrayBuilder getAllScenario();
     JsonArrayBuilder getScenarioJson(String id);
    //JsonArrayBuilder getScenarioJson(String id);
    Scenario AddScenario(Scenario scenario);
    void testProduitStock(String id ,int rang,String idScenarioRecord) throws IOException;
    void testRechercheClient(String id,int rang,String idScenarioRecord) throws IOException;
    void testPlaningLiv(String id,int rang,String idScenarioRecord) throws IOException;
    void testAjoutProduit(String id,int rang,String idScenarioRecord) throws IOException;
    void testrafraichir(String id,int rang,String idScenarioRecord) throws IOException;
    void testValiderModeLiv(String id,int rang,String idScenarioRecord) throws IOException;
    void associerClient(String id,int rang,String idScenarioRecord) throws IOException;
    void testvaliderVendeur(String id,int rang,String idScenarioRecord) throws IOException;
    void testParametrage(String id,int rang,String idScenarioRecord) throws IOException;
    void testRechercheCp(String id,int rang,String idScenarioRecord) throws IOException;
    void testDerniereCommande(String id,int rang,String idScenarioRecord) throws IOException;
    void testvalider(String id,int rang,String idScenarioRecord) throws IOException;
    void testLogin(String id,int rang,String idScenarioRecord) throws IOException;





}
