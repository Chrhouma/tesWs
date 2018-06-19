package com.pictimegroupe.FrontVendeur.testWebservice;
import org.hibernate.annotations.Cascade;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class ServiceRecord {

    public ServiceRecord() {
    }

    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne (fetch = FetchType.LAZY)
    private WebService webService;
    private Integer rang;
    private Date date;
    private Date executionTime;
    private String resultPath;

    @ManyToOne (fetch = FetchType.LAZY)
    private  ScenarioRecord scenarioRecord;
   /*@ManyToOne (fetch = FetchType.LAZY)
   private Scenario scenario;*/
    private String status;

    @OneToMany(fetch = FetchType.LAZY ,mappedBy = "serviceRecord")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private List<Delta> deltas=new LinkedList();

    public Integer getRang() {
        return rang;
    }

    public void setRang(Integer rang) {
        this.rang = rang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ScenarioRecord getScenarioRecord() {
        return scenarioRecord;
    }

    public void setScenarioRecord(ScenarioRecord scenarioRecord) {
        this.scenarioRecord = scenarioRecord;
    }

    public WebService getWebService() {
        return webService;
    }

    public void setWebService(WebService webService) {
        this.webService = webService;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Delta> getDeltas() {
        return deltas;
    }

    public void setDeltas(List<Delta> delta) {
        deltas = delta;
    }


    public String getFormattedDate(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy � HH:mm");
        return formater.format(this.date);
    }
    public JsonObjectBuilder getServiceRecordJson(){
        JsonArrayBuilder deltasArray =Json.createArrayBuilder();
        JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();
        jsonObjectBuilder.add("idWebService",  this.getWebService().getId());
        jsonObjectBuilder.add("rangWebService",this.getWebService().getRang());
        jsonObjectBuilder.add("date", this.getFormattedDate(this.getDate()));
        jsonObjectBuilder.add("executionTime", this.getFormattedDate(this.getExecutionTime()));
        jsonObjectBuilder.add("resultPath",this.getResultPath());
        jsonObjectBuilder.add("status", this.getStatus());

       /* for (Delta delta: Deltas) {
            deltasArray.add(delta.getDeltaJson());
        }*/
        jsonObjectBuilder.add("deltas",deltasArray);
        return jsonObjectBuilder;
    }
}
