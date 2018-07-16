package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.Delta;


import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.List;

public interface DeltaServices  {
    Delta getDelta(String id);

    JsonArrayBuilder getDeltaByIdServiceRecord(String id);
    void addDelta(Delta delta);
    public int  getNbDelta(String idScenarioRecord);
    JsonArrayBuilder getAllDeltaByIdeScenarioRcordAutomatictest(String idScenarioRecord1,String scenarioName);
    JsonArrayBuilder getAllDeltaByIdeScenarioRcord(String idScenarioRecord1);
}
