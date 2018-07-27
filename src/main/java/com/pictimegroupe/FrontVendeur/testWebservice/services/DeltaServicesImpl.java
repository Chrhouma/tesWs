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

/**
 *
 */
@Service
public class DeltaServicesImpl implements  DeltaServices {
    @Autowired
    DeltaRepository deltaRepository;
    @Autowired
    ServiceRecordServices serviceRecordServices;

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Delta getDelta(String id) {

        List<Delta>  deltaList=  deltaRepository.findAllById(id);
        return deltaList.get(0);

    }

    /**
     *
     * @param id
     * @return
     */
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

    /**
     *
     * @param idScenarioRecord1
     * @return
     */
    @Override
    public JsonArrayBuilder getAllDeltaByIdeScenarioRcordAutomatictest(String idScenarioRecord1,String scenarioName) {
        JsonArrayBuilder deltaArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder deltaArrayBuilder1 = Json.createArrayBuilder();
        JsonObjectBuilder nbWebService=Json.createObjectBuilder();
        List<ServiceRecord> serviceRecords = serviceRecordServices.getAllServiceRecordByScenarioRecord(idScenarioRecord1);
        List <Delta> deltaList= (List<Delta>) deltaRepository.findAll();

        int nbWebServiceModifie=0;
        int nbwebSericeTotale=serviceRecords.size();

         for(Delta delta:deltaList){
           for (ServiceRecord serviceRecord:serviceRecords){
                if(delta.getServiceRecord().getId().equals(serviceRecord.getId())){
                    nbWebServiceModifie++;
                    deltaArrayBuilder.add( delta.getDeltoJson());
                }
            }
        }
        double tauxModification= (new Double(nbWebServiceModifie*100) / new Double(nbwebSericeTotale));
        double tauxModificationArondi= Math.round((tauxModification*1000)/1000);
        nbWebService.add("scenarioName",scenarioName);
        nbWebService.add("tauxModification",tauxModificationArondi);
        nbWebService.add("nbmodification",nbWebServiceModifie);
        nbWebService.add("nbwebServiceTotale",nbwebSericeTotale);
        nbWebService.add("deltas",deltaArrayBuilder);
        //deltaArrayBuilder1.add(Integer.toString(nbWebServiceModifie));
        deltaArrayBuilder1.add(nbWebService);
        return deltaArrayBuilder1;
    }

    //Ecrire une méthode qui permet de renvoyer de le taux de modification totale

    @Override
    public int  getNbDelta(String idScenarioRecord){
        List <Delta> deltaList= (List<Delta>) deltaRepository.findAll();
        List<ServiceRecord> serviceRecords = serviceRecordServices.getAllServiceRecordByScenarioRecord(idScenarioRecord);
        int nbdelta=0;
        for(Delta delta:deltaList){
            for (ServiceRecord serviceRecord:serviceRecords){
                if(delta.getServiceRecord().getId().equals(serviceRecord.getId())){
                    nbdelta++;
                }
            }
        }
        return nbdelta;
    }

    @Override
    public JsonArrayBuilder getAllDeltaByIdeScenarioRcord(String idScenarioRecord1) {
        JsonArrayBuilder deltaArrayBuilder = Json.createArrayBuilder();
        List<ServiceRecord> serviceRecords = serviceRecordServices.getAllServiceRecordByScenarioRecord(idScenarioRecord1);
        List <Delta> deltaList= (List<Delta>) deltaRepository.findAll();

        for(Delta delta:deltaList){
            for (ServiceRecord serviceRecord:serviceRecords){
                if(delta.getServiceRecord().getId().equals(serviceRecord.getId())){

                    deltaArrayBuilder.add(delta.getDeltoJson());
                }
            }
        }
        return deltaArrayBuilder;
    }
    @Override
    public JsonArrayBuilder getAllDeltaByIdServiceRecord(String idServiceRecord1) {
        JsonArrayBuilder deltaArrayBuilder = Json.createArrayBuilder();
        List <Delta> deltaList= (List<Delta>) deltaRepository.findAll();
        for(Delta delta:deltaList) {

            if(delta.getServiceRecord().getId().equals(idServiceRecord1)){

                deltaArrayBuilder.add(delta.getDeltoJson());
            }
        }
        return deltaArrayBuilder;
    }


    /**
     *
     * @param delta
     */
    @Override
    public void addDelta(Delta delta) {
  deltaRepository.save(delta);
    }

    @Override
    public boolean existedDelta(String expectedValue, String registeredValue,String  idserviceRecord){
        List<Delta> deltaList= (List<Delta>) deltaRepository.findAll();
        System.out.println("taille de deltat"+deltaList.size());
        boolean exist= false;
        int i=0;
        while(exist==false && i<deltaList.size()){

            Delta delta=deltaList.get(i);

            if(delta.getServiceRecord().getId().equals(idserviceRecord) && delta.getExpctedValue().equals(expectedValue) && delta.getRegisteedValue().equals(registeredValue)) {
                exist= true;

            }
            i++;
        }
        return exist;
    }
}
