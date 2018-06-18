package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.Scenario;
import com.pictimegroupe.FrontVendeur.testWebservice.WebService;

import javax.json.JsonArrayBuilder;
import java.io.IOException;
import java.util.List;

public interface ScenarioService  {

    Scenario getScenario(String id);
    List<String> getWebServiceNamesByIdScenario(String id);

    List <Integer> getWebServiceRangByIdScenario(String id);
    String getWebServiceNamesByRang(String id,int rang);
    JsonArrayBuilder getAllScenario();

    Scenario AddScenario(Scenario scenario);
    void testProduitStock(String id ,int rang) throws IOException;
    void testRechercheClient(String id,int rang) throws IOException;
    void testPlaningLiv(String id,int rang) throws IOException;
    void testAjoutProduit(String id,int rang) throws IOException;
    void testrafraichir(String id,int rang) throws IOException;
    void testValiderModeLiv(String id,int rang) throws IOException;
    void associerClient(String id,int rang) throws IOException;
    void testvaliderVendeur(String id,int rang) throws IOException;
    void testParametrage(String id,int rang) throws IOException;
    void testRechercheCp(String id,int rang) throws IOException;
    void testDerniereCommande(String id,int rang) throws IOException;
    void testvalider(String id,int rang) throws IOException;
    void testLogin(String id,int rang) throws IOException;





}
