package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.ScenarioRecord;
import com.pictimegroupe.FrontVendeur.testWebservice.ServiceRecord;
import com.pictimegroupe.FrontVendeur.testWebservice.WebServiceScenario;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.ScenarioRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.File;
import java.io.IOException;
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


    @Override
    public ScenarioRecord getScenarioRecord(String id) {
        List<ScenarioRecord> recordList=scenarioRecordRepository.findScenarioRecordById(id);
        return recordList.get(0);
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
        //Scenario scenario=this.getScenario(id);
        //System.out.println(scenario.getWebServices().size());
        WebServiceScenario  webServiceScenario= new WebServiceScenario();
        ScenarioRecord scenarioRecord= new ScenarioRecord();
        scenarioRecord.setDate(date.actuelle);
        scenarioRecord.setScenario(scenarioService.getScenario(id));
        scenarioRecord.setExecutionTime(date.executionTime);
        scenarioRecord.setStatus("ok");
        addScenarioRecord(scenarioRecord);

        System.out.println(scenarioService.getScenario(id));
        List<String> webServiceNameList = scenarioService.getWebServiceNamesByIdScenario(id);
        List<Integer> webServiceRang=scenarioService.getWebServiceRangByIdScenario(id);

        for (int rang=0;rang< webServiceRang.size();rang++) {


            String name=scenarioService.getWebServiceNamesByRang(id,rang+1);
            System.out.println("le nom du rang "+(rang+1) +"est "+ name);
            switch (name) {
                case "produitStock":
                    scenarioService.testProduitStock(id,rang+1);
                    System.out.println("le produitStock c marche");
                    break;
                case "rechercheclient":
               //    scenarioService.testRechercheClient(id,i);
                //    System.out.println("le rechercheclient c marche");
                    break;
                case"PlaningLiv":
                    scenarioService.testPlaningLiv(id,rang+1);
                    System.out.println("le PlaningLiv c marche");
                    break;
                case"ajoutProduit":
                    scenarioService.testAjoutProduit(id,rang+1);
                    System.out.println("le ajoutProduit c marche");
                    break;
                case"rafraichir":
                    scenarioService.testrafraichir(id,rang);
                    System.out.println("le rafraichir c marche");
                    break;
                case"ValiderModeLiv":
                    scenarioService.testValiderModeLiv(id,rang+1);
                    System.out.println("le ValiderModeLiv c marche");
                    break;
                case"associerClient":
                    scenarioService.associerClient(id,rang+1);
                    System.out.println("le associerClient c marche");
                    break;
                case"validerVendeur":
                    scenarioService.testvaliderVendeur(id,rang+1);
                    System.out.println("le validerVendeur c marche");
                    break;
                case"parametrage":
//                    scenarioService.testParametrage(scenarioRecord.getId());
                    System.out.println("le parametrage c marche");
                    break;
                case"rechercheCp":
                    scenarioService.testRechercheCp(id,rang+1);
                    System.out.println("le rechercheCp c marche");
                    break;
                case"DerniereCommande":
                    scenarioService.testDerniereCommande(id,rang+1);
                    System.out.println("le DerniereCommande c marche");
                    break;
                case"valider":
                    scenarioService.testvalider(id,rang+1);
                    System.out.println("le valider c marche");
                    break;

                case "login":
                    scenarioService.testLogin(id,rang+1);
                    //testWs.testerFonction();
                    System.out.println("le login c marche");
                    break;
            }
        }
    }

    @Override
    public void comparerScenario(String idScenarioRecord1, String idScenarioRecord2) throws IOException {
        List<ServiceRecord> serviceRecords1 =serviceRecordServices.getAllServiceRecordByScenario(idScenarioRecord1);
        List<ServiceRecord> serviceRecords2 =serviceRecordServices.getAllServiceRecordByScenario(idScenarioRecord2);
        int nb=0;
        for(int i=0; i<serviceRecords1.size();i++){
            String path1=serviceRecords1.get(i).getResultPath();
            for(int j =0;j<serviceRecords2.size();j++){
                String path2=serviceRecords2.get(j).getResultPath();

                if(path1.substring(0,path1.length()-16).equals(path2.substring(0,path2.length()-16))){

                    compare.simpleCompare(path1,path2);
                    System.out.println("je compare"+ nb++ );
                    System.out.println(path1.substring(0,path1.length()-16));
                    System.out.println(path1 +"        "+path2 );
                }
            }
          //  String path2=serviceRecords2.get(i).getResultPath();

           //compare.simpleCompare(path1,path2);

          // System.out.println(  path1.substring(0,path1.length()-16));


        }
    }
}
