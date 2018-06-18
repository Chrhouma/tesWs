package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.Scenario;
import com.pictimegroupe.FrontVendeur.testWebservice.WebService;

import javax.json.JsonArrayBuilder;
import java.io.IOException;
import java.util.List;

public interface ScenarioService  {

    Scenario getScenario(String id);
    List<String> getWebServiceNamesByIdScenario(String id);

    JsonArrayBuilder getAllScenario();
    Scenario AddScenario(Scenario scenario);
    void testProduitStock(String id) throws IOException;
    void testRechercheClient(String id) throws IOException;
    void testPlaningLiv(String id) throws IOException;
    void testAjoutProduit(String id) throws IOException;
    void testrafraichir(String id) throws IOException;
    void testValiderModeLiv(String id) throws IOException;
    void associerClient(String id) throws IOException;
    void testvaliderVendeur(String id) throws IOException;
    void testParametrage(String id) throws IOException;
    void testRechercheCp(String id) throws IOException;
    void testDerniereCommande(String id) throws IOException;
    void testvalider(String id) throws IOException;
    void testLogin(String id) throws IOException;





}
