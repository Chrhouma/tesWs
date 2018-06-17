package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.ScenarioRecord;

import javax.json.JsonArrayBuilder;
import java.io.IOException;

public interface ScenarioRecordService {

    ScenarioRecord getScenarioRecord(String id);
    JsonArrayBuilder getAllScenarioRecord();
    void addScenarioRecord(ScenarioRecord ScenarioRecord);

    void testerScenario(String id) throws IOException;
    void comparerScenario(String idScenarioRecord, String IdScenarioRecord) throws IOException;
}
