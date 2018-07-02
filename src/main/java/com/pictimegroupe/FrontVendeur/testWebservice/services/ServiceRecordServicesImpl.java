package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.Delta;
import com.pictimegroupe.FrontVendeur.testWebservice.ServiceRecord;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.DeltaRepository;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.ServiceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.*;

@Service
public class ServiceRecordServicesImpl implements ServiceRecordServices {
    @Autowired
    ServiceRecordRepository serviceRecordRepository;

    public ServiceRecordServicesImpl() {
    }

    @Autowired
    DeltaRepository deltaRepository;
    @Override
    public ServiceRecord getServiceRecord(String id){
        List<ServiceRecord> serviceRecordList =serviceRecordRepository.findServiceRecordsById(id);
        return serviceRecordList.get(0);
    }

    @Override
    public JsonArrayBuilder getAllServiceRecord() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        List<ServiceRecord> serviceRecordList =(List<ServiceRecord>) serviceRecordRepository.findAll();
        for (ServiceRecord serviceRecord :serviceRecordList) {
            JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();
            jsonObjectBuilder.add("id",serviceRecord.getId());
            jsonObjectBuilder.add("idWebService",  serviceRecord.getWebService().getId());
            jsonObjectBuilder.add("rangWebService",serviceRecord.getWebService().getRang());
            jsonObjectBuilder.add("date","25/02/2011");
            jsonObjectBuilder.add("executionTime","25/02/2011");
            jsonObjectBuilder.add("resultPath",serviceRecord.getResultPath());
            jsonObjectBuilder.add("status",serviceRecord.getStatus());
            List<Delta> deltaList=(List<Delta>) deltaRepository.findAll();//qppel q lq ;ethode des repository delqtq pour trouver lq liste des id de chqaue difference
            for ( Delta delta :deltaList){
                if(delta.getServiceRecord().getId().equals(serviceRecord.getId())) {
                    jsonObjectBuilder.add("delta", delta.getId());
                }
            }
            arrayBuilder.add(jsonObjectBuilder);
        }
        return arrayBuilder;
    }
    @Override
    public List getWebServiceRecordByWebServiceId(String id) {
        List<ServiceRecord> serviceRecords= (List<ServiceRecord>) serviceRecordRepository.findAll();

        List<ServiceRecord> recordList= new LinkedList<>();
        for(ServiceRecord serviceRecord:serviceRecords){

            if(serviceRecord.getWebService().getId().equals(id)){
                recordList.add(serviceRecord);
            }

        }
        return recordList;
    }
    @Override
    public void addServiceRecord(ServiceRecord serviceRecord) {
        serviceRecordRepository.save(serviceRecord);
    }

    @Override
    public List<ServiceRecord> getAllServiceRecordByScenarioRecord(String idScnearioRecord) {
        List <ServiceRecord> serviceRecords= (List<ServiceRecord>) serviceRecordRepository.findAll();
        List <ServiceRecord> serviceRecords1=new LinkedList<>();
            for(int i=0;i<serviceRecords.size();i++){
                if(serviceRecords.get(i).getScenarioRecord().getId().equals(idScnearioRecord)) {
                    serviceRecords1.add(serviceRecords.get(i));
                }
             }

             return serviceRecords1;
    }


}
