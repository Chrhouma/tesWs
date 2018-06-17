package com.pictimegroupe.FrontVendeur.testWebservice.repository;

import com.pictimegroupe.FrontVendeur.testWebservice.Scenario;
import com.pictimegroupe.FrontVendeur.testWebservice.WebService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScenarioRepository extends CrudRepository<Scenario,String> {

   List<Scenario> findAllById(String id);
}
