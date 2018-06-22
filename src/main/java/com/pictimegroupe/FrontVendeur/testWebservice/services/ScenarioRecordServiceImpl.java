package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.ScenarioRecord;
import com.pictimegroupe.FrontVendeur.testWebservice.ServiceRecord;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.ScenarioRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class ScenarioRecordServiceImpl implements ScenarioRecordService {
    private Dates date=new Dates();
    @Autowired
    ScenarioRecordRepository scenarioRecordRepository;
    @Autowired
    ScenarioService scenarioService;
    @Autowired
    ServiceRecordServices serviceRecordServices;
   @Autowired
   Compare compare;
   @Autowired
   DeltaServices deltaServices;



    @Override
    public ScenarioRecord getScenarioRecord(String id) {
        List<ScenarioRecord> recordList=scenarioRecordRepository.findScenarioRecordById(id);
        return recordList.get(0);
    }

    @Override
    public List getScenarioRecordByScenario(String id) {
        List<ScenarioRecord> scenarioRecords= (List<ScenarioRecord>) scenarioRecordRepository.findAll();
        System.out.println("taille"+scenarioRecords.size());
        List<ScenarioRecord> recordList= new LinkedList<>();
        for(ScenarioRecord scenarioRecord:scenarioRecords){
            if(scenarioRecord.getId().equals(id)){
                recordList.add(scenarioRecord);
            }
        }
        return recordList;
    }

    @Override
    public JsonArrayBuilder getAllScenarioRecord() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        List<ScenarioRecord> recordList= (List<ScenarioRecord>) scenarioRecordRepository.findAll();
        for (ScenarioRecord scenarioRecord:recordList){
            JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();
            jsonObjectBuilder.add("id",scenarioRecord.getId());
            jsonObjectBuilder.add("date","25/02/2011");
            jsonObjectBuilder.add("executionTime","25/02/2011");
           // jsonObjectBuilder.add("status",scenarioRecord.getStatus());
           // jsonObjectBuilder.add("scenario", (JsonObjectBuilder) scenarioRecord.getScenario());
            arrayBuilder.add(jsonObjectBuilder);
        }
        return arrayBuilder;
    }

    @Override
    public void addScenarioRecord(ScenarioRecord scenarioRecord) {
    scenarioRecordRepository.save(scenarioRecord);
    }

    @Override
    public void testerScenario(String id) throws IOException {

        ScenarioRecord scenarioRecord= new ScenarioRecord();
        scenarioRecord.setDate(date.actuelle);
        scenarioRecord.setScenario(scenarioService.getScenario(id));
        scenarioRecord.setExecutionTime(date.executionTime);
        scenarioRecord.setStatus("ok");
        addScenarioRecord(scenarioRecord);

        System.out.println(scenarioService.getScenario(id));
        List<Integer> webServiceRang=scenarioService.getWebServiceRangByIdScenario(id);

        for (int rang=0;rang< webServiceRang.size();rang++) {
            String name=scenarioService.getWebServiceNamesByRang(id,rang+1).get(0);
            switch (name) {
                case "produitStock":
                    scenarioService.testProduitStock(id,rang+1,scenarioRecord.getId());

                    break;
                case "rechercheclient":
               //    scenarioService.testRechercheClient(id,i);
                //    System.out.println("le rechercheclient c marche");
                    break;
                case"PlaningLiv":
                    scenarioService.testPlaningLiv(id,rang+1,scenarioRecord.getId());

                    break;
                case"ajoutProduit":
                    scenarioService.testAjoutProduit(id,rang+1,scenarioRecord.getId());
                    ;
                    break;
                case"rafraichir":
                    scenarioService.testrafraichir(id,rang+1,scenarioRecord.getId());

                    break;
                case"ValiderModeLiv":
                    scenarioService.testValiderModeLiv(id,rang+1,scenarioRecord.getId());

                    break;
                case"associerClient":
                    scenarioService.associerClient(id,rang+1,scenarioRecord.getId());
                    break;
                case"validerVendeur":
                    scenarioService.testvaliderVendeur(id,rang+1,scenarioRecord.getId());
                    break;
                case"parametrage":
//                    scenarioService.testParametrage(scenarioRecord.getId());
                    break;
                case"rechercheCp":
                    scenarioService.testRechercheCp(id,rang+1,scenarioRecord.getId());
                    break;
                case"DerniereCommande":
                    scenarioService.testDerniereCommande(id,rang+1,scenarioRecord.getId());
                    break;
                case"valider":
                    scenarioService.testvalider(id,rang+1,scenarioRecord.getId());
                    break;

                case "login":
                    scenarioService.testLogin(id,rang+1,scenarioRecord.getId());
                    break;
            }
        }
    }

    @Override
    public void comparerScenario(String idScenarioRecord1, String idScenarioRecord2) throws IOException {
        List<ServiceRecord> serviceRecords1 =serviceRecordServices.getAllServiceRecordByScenarioRecord(idScenarioRecord1);
        List<ServiceRecord> serviceRecords2 =serviceRecordServices.getAllServiceRecordByScenarioRecord(idScenarioRecord2);

        for(int i=0; i<serviceRecords1.size();i++) {

              for (int j = 0; j < serviceRecords2.size(); j++) {
                if (serviceRecords1.get(i).getRang() == serviceRecords2.get(j).getRang()) {
                    String path1=serviceRecords1.get(i).getResultPath();
                    String path2=serviceRecords2.get(j).getResultPath();

                    compare.comparaison(path1,path2,serviceRecords1.get(i).getId());

                }
            }
        }

    }
}
