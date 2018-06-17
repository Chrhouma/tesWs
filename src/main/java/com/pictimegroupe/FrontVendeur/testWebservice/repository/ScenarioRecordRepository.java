package com.pictimegroupe.FrontVendeur.testWebservice.repository;

import com.pictimegroupe.FrontVendeur.testWebservice.ScenarioRecord;
import com.pictimegroupe.FrontVendeur.testWebservice.ServiceRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScenarioRecordRepository extends CrudRepository<ScenarioRecord, String> {

    List<ScenarioRecord> findScenarioRecordById(String id);

}
