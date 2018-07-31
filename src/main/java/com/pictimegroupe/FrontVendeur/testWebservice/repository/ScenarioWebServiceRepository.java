package com.pictimegroupe.FrontVendeur.testWebservice.repository;

import com.pictimegroupe.FrontVendeur.testWebservice.Scenario;
import com.pictimegroupe.FrontVendeur.testWebservice.WebServiceScenario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ScenarioWebServiceRepository extends CrudRepository<WebServiceScenario,String> {
    List<WebServiceScenario> findAllByScenario(Scenario id);

  //  @Query("DELETE FROM WEB_SERVICE_SCENARIO WHERE \"ID_SCENARIO\" LIKE 'idScenario'")
     // void delteAllWebserviceScenarioByIdscenario(String idScenario);
}
