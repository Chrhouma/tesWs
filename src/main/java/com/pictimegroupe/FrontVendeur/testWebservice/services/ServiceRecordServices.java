package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.ServiceRecord;

import javax.json.JsonArrayBuilder;
import java.util.List;

public interface ServiceRecordServices  {
    ServiceRecord getServiceRecord(String id);
    JsonArrayBuilder getAllServiceRecord();
    List getWebServiceRecordByWebServiceId(String id);
    void addServiceRecord(ServiceRecord serviceRecord);
    List<ServiceRecord> getAllServiceRecordByScenarioRecord(String idScnearioRecord);



}
