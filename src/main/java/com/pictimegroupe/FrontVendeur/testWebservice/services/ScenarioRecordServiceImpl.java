package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.Scenario;
import com.pictimegroupe.FrontVendeur.testWebservice.ScenarioRecord;
import com.pictimegroupe.FrontVendeur.testWebservice.ServiceRecord;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.ScenarioRecordRepository;
import com.pictimegroupe.FrontVendeur.testWebservice.repository.ScenarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import com.pictimegroupe.FrontVendeur.testWebservice.Util.Const;

/**
 *
 */
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
    @Autowired
     ScenarioRepository scenarioRepository;
    @Autowired
    SendEmailService sendEmailService;

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ScenarioRecord getScenarioRecord(String id) {
        List<ScenarioRecord> recordList=scenarioRecordRepository.findScenarioRecordById(id);
        return recordList.get(0);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public List getScenarioRecordByScenario(String id) {
        List<ScenarioRecord> scenarioRecords= (List<ScenarioRecord>) scenarioRecordRepository.findAll();

        List<ScenarioRecord> recordList= new LinkedList<>();
        for(ScenarioRecord scenarioRecord:scenarioRecords){

            if(scenarioRecord.getScenario().getId().equals(id)){
                recordList.add(scenarioRecord);
            }
        }
        return recordList;
    }

    /**
     *
     * @return
     */
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
            arrayBuilder.add(jsonObjectBuilder);
        }
        return arrayBuilder;
    }

    /**
     *
     * @param scenarioRecord
     */
    @Override
    public void addScenarioRecord(ScenarioRecord scenarioRecord) {
    scenarioRecordRepository.save(scenarioRecord);
    }

    @Override
    public void testerScenarioAut(String idScenario) throws IOException {

        Dates date= new Dates();
        ScenarioRecord scenarioRecord= new ScenarioRecord();
        scenarioRecord.setDate(date.actuelle);
        scenarioRecord.setScenario(scenarioService.getScenario(idScenario));
        scenarioRecord.setExecutionTime(date.executionTime);
        scenarioRecord.setStatus("ok");
        addScenarioRecord(scenarioRecord);
        List<Integer> webServiceRang=scenarioService.getWebServiceRangByIdScenario(idScenario);

        for (int rang=0;rang< webServiceRang.size();rang++) {
            if(rang<1){
                scenarioService.testLogin(idScenario,rang,scenarioRecord.getId());
            }
            else {
                String idwebServcie = scenarioService.getWebServiceIdByRang(idScenario, rang + 1).get(0);
                scenarioService.tester(idScenario, rang + 1, scenarioRecord.getId(), idwebServcie);
            }
        }
    }

    @Override
    public void testerScenario(String id) throws IOException {
        Dates date= new Dates();
        ScenarioRecord scenarioRecord= new ScenarioRecord();
        scenarioRecord.setDate(date.actuelle);
        scenarioRecord.setScenario(scenarioService.getScenario(id));
        scenarioRecord.setExecutionTime(date.executionTime);
        scenarioRecord.setStatus("ok");
        addScenarioRecord(scenarioRecord);

            List<Integer> webServiceRang=scenarioService.getWebServiceRangByIdScenario(id);
        for (int rang=0;rang< webServiceRang.size();rang++) {
            String name=scenarioService.getWebServiceNamesByRang(id,rang+1).get(0);

            switch (name) {
                case "produitStock":
                    scenarioService.testProduitStock(id,rang+1,scenarioRecord.getId());

                    break;
                case "rechercheclient":
                   scenarioService.testRechercheClient(id,rang+1,scenarioRecord.getId());

                    break;
                case"PlaningLiv":
                    scenarioService.testPlaningLiv(id,rang+1,scenarioRecord.getId());

                    break;
                case"ajoutProduit":
                    scenarioService.testAjoutProduit(id,rang+1,scenarioRecord.getId());

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
                  scenarioService.testParametrage(id,rang+1,scenarioRecord.getId());
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

    /**
     *
     * @param recordList
     * @return
     */
    // methode qui permet de trier la liste des scenario record avec la date d'exécution
    @Override
    public String sortListrecordByDAte(List<ScenarioRecord> recordList){
        Collections.sort(recordList, Comparator.comparing(ScenarioRecord::getDate));
        String idScenarioRecord1=recordList.get(recordList.size()-1).getId();
        String idScenarioRecord2=recordList.get(recordList.size()-2).getId();
        try {
            comparerScenario(idScenarioRecord1,idScenarioRecord2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return idScenarioRecord2;
    }

    /**
     *
     * @throws IOException
     */
    @Override
    public void testerAllScenario() throws IOException {
        List <Scenario> scenarioList= (List<Scenario>) scenarioRepository.findAll();
        for(Scenario scenario:scenarioList){
            //this.testerScenario(scenario.getId());
            this.testerScenarioAut(scenario.getId());
        }
    }

    /**
     *
     * @return
     * @throws IOException
     */
    @Override
    public String compareAutomatic() throws IOException {
        JsonArrayBuilder jsonArrayBuilder =Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        //tetster tous les scénarios
        testerAllScenario();
        List <Scenario> scenarioList= (List<Scenario>) scenarioRepository.findAll();
        int nbWstotale=scenarioList.size();
        //pour chaque scénarios je cherche sa liste de scenario records
        for(Scenario scenario :scenarioList){
            JsonObjectBuilder obj = Json.createObjectBuilder();
            // trouver les scenario record pour chaque scenrio
            List<ScenarioRecord> scenarioRecordList;
            scenarioRecordList=findScenarioRecordsByIdScenario(scenario.getId());
            //trier la liste de scenario Record selon la date
            String idScenarioRecord=  sortListrecordByDAte(scenarioRecordList);
            obj.add("modification",  deltaServices.getAllDeltaByIdeScenarioRcordAutomatictest(idScenarioRecord,scenario.getName()));
            // jsonArrayBuilder.add(obj);
            jsonArrayBuilder.add(obj);
        }
        jsonObjectBuilder.add("tests",jsonArrayBuilder);
        return jsonObjectBuilder.build().toString();
    }
    // methode qui permet de calculer le nombre de modificaton pour envoyer un mail
    @Override
    public void sendingDeltaMail()throws IOException{
        testerAllScenario();
        List <Scenario> scenarioList= (List<Scenario>) scenarioRepository.findAll();
        for(Scenario scenario :scenarioList){
            List<ScenarioRecord> scenarioRecordList;
            scenarioRecordList=findScenarioRecordsByIdScenario(scenario.getId());
            String idScenarioRecord=  sortListrecordByDAte(scenarioRecordList);
            int nbdelta=deltaServices.getNbDelta(idScenarioRecord);
            if(nbdelta>Const.TAUXMODIFICATION){
                sendEmailService.sendDelta();
                System.out.println("mail envoyé");
            }
            else{
                System.out.println("taux de modification négligeable");
            }
        }

    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public List<ScenarioRecord> findScenarioRecordsByIdScenario(String id){
        List <ScenarioRecord> recordList= new LinkedList<>();
        List<ScenarioRecord>AllscenarioRecords= (List<ScenarioRecord>) scenarioRecordRepository.findAll();
        for(ScenarioRecord scenarioRecord:AllscenarioRecords){
            if(scenarioRecord.getScenario().getId().equals(id)){
                recordList.add(scenarioRecord);
            }
        }
        return recordList;
    }

    /**
     *
     * @param idScenarioRecord1
     * @param idScenarioRecord2
     * @throws IOException
     */
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
