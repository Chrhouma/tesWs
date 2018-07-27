package com.pictimegroupe.FrontVendeur.testWebservice;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
public class ScenarioRecord {
    @Id
    private String id =UUID.randomUUID().toString();
    private Date executionTime;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;
    @OneToOne (fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Scenario scenario;

    private String status;

    public ScenarioRecord() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }
    public String getFormattedDate(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy � HH:mm");
        return formater.format(this.date);
    }

    public JsonObjectBuilder getSenarioRecordJson(){
        JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();
        jsonObjectBuilder.add("id",this.getId());
        jsonObjectBuilder.add("date", this.getFormattedDate(this.getDate()));
        jsonObjectBuilder.add("executionTime", this.getFormattedDate(this.getExecutionTime()));
        jsonObjectBuilder.add("status", this.getStatus());
        jsonObjectBuilder.add("scenarioId",  this.getScenario().getId());

        return jsonObjectBuilder;
    }

    public int compareScenarioRecordWithDate(ScenarioRecord scenarioRecord){
        return  getDate().compareTo(scenarioRecord.getDate());
    }

}
