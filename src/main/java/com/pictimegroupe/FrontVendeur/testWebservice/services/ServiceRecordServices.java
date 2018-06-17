package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.ServiceRecord;

import javax.json.JsonArrayBuilder;
import java.util.List;

public interface ServiceRecordServices  {
    ServiceRecord getServiceRecord(String id);
    JsonArrayBuilder getAllServiceRecord();
    void addServiceRecord(ServiceRecord serviceRecord);
    List<ServiceRecord> getAllServiceRecordByScenario(String idScnearioRecord);



}
