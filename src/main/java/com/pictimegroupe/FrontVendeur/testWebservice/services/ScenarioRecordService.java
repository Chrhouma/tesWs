package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.ScenarioRecord;

import javax.json.JsonArrayBuilder;
import java.io.IOException;
import java.util.List;

public interface ScenarioRecordService {

    ScenarioRecord getScenarioRecord(String id);
    List getScenarioRecordByScenario(String id);
    List<ScenarioRecord> findScenarioRecordsByIdScenario(String id);
    JsonArrayBuilder getAllScenarioRecord();
    void addScenarioRecord(ScenarioRecord ScenarioRecord);
    String sortListrecordByDAte(List<ScenarioRecord> recordList);
    void testerScenario(String id) throws IOException;
    void testerAllScenario() throws IOException;
    void comparerScenario(String idScenarioRecord, String IdScenarioRecord) throws IOException;
    String compareAutomatic() throws IOException;
}
