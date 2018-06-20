package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.Delta;
import com.pictimegroupe.FrontVendeur.testWebservice.ServiceRecord;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.DeltaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.List;

@Service
public class DeltaServicesImpl implements  DeltaServices {
    @Autowired
    DeltaRepository deltaRepository;
    @Autowired
    ServiceRecordServices serviceRecordServices;
    @Override
    public Delta getDelta(String id) {

        List<Delta>  deltaList=  deltaRepository.findAllById(id);
        return deltaList.get(0);

    }



    @Override
    public JsonArrayBuilder getDeltaByIdServiceRecord(String id) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        List <Delta> deltaList = (List<Delta>) deltaRepository.findAll();
         for (Delta delta:deltaList){
             JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();
            if(delta.getServiceRecord().getId().equals(id)){
                jsonObjectBuilder.add("idDelta",delta.getId());
                jsonObjectBuilder.add("node",delta.getNode());
                jsonObjectBuilder.add("expctedValue",delta.getExpctedValue());
                jsonObjectBuilder.add("registeredValue",delta.getRegisteedValue());
                 }
             arrayBuilder.add(jsonObjectBuilder);
        }

        return arrayBuilder;
    }

    @Override
    public JsonArrayBuilder getAllDeltaByIdeScenarioRcord(String idScenarioRecord1) {
        JsonArrayBuilder deltaArrayBuilder = Json.createArrayBuilder();
        List<ServiceRecord> serviceRecords = serviceRecordServices.getAllServiceRecordByScenarioRecord(idScenarioRecord1);

        for (int i=0;i<serviceRecords.size();i++) {
            /*if(serviceRecords.get(i).getScenarioRecord().getId().equals(idScenarioRecord1)){

               deltaArrayBuilder.add(getDeltaByIdServiceRecord(idScenarioRecord1));
            }*/
            // je doit trouver la liste de s deltas

            System.out.println(this.getDeltaByIdServiceRecord(serviceRecords.get(i).getId()).build().toString());
            System.out.println("****************");
        }
        return deltaArrayBuilder;
    }

    @Override
    public void addDelta(Delta delta) {
  deltaRepository.save(delta);
    }
}
