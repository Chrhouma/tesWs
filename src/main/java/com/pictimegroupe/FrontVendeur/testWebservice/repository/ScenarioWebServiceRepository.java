package com.pictimegroupe.FrontVendeur.testWebservice.repository;

import com.pictimegroupe.FrontVendeur.testWebservice.Scenario;
import com.pictimegroupe.FrontVendeur.testWebservice.WebServiceScenario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.Repository;

import java.util.List;


    public interface ScenarioWebServiceRepository  extends Repository<WebServiceScenario,String> {
    List<WebServiceScenario> findAllByScenario(Scenario id);
    String deleteByScenario(Scenario scenario);

}
