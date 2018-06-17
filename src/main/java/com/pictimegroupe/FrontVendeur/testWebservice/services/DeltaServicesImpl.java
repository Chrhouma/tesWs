package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.Delta;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.DeltaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.LinkedList;
import java.util.List;

@Service
public class DeltaServicesImpl implements  DeltaServices {
    @Autowired
    DeltaRepository deltaRepository;
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
    public void addDelta(Delta delta) {
  deltaRepository.save(delta);
    }
}
