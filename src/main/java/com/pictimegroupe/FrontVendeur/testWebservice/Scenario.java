package com.pictimegroupe.FrontVendeur.testWebservice;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CascadeType;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table (name="Senarios")
public class Scenario implements Serializable {
    @Id
    private String id =UUID.randomUUID().toString();

    private String name;
    private String cron;
    @OneToMany(mappedBy="scenario", cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REMOVE})
    private List<WebServiceScenario> webServicesScenario;


    public Scenario() {
    }


    public List<WebServiceScenario> getWebServicesScenario() {
        return webServicesScenario;
    }

    public void setWebServicesScenario(List<WebServiceScenario> webServicesScenario) {
        this.webServicesScenario = webServicesScenario;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public JsonObjectBuilder getScenarioJson() {

        JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();
        JsonArrayBuilder webserviceArray =Json.createArrayBuilder();
        JsonArrayBuilder scenarioRecordArray =Json.createArrayBuilder();
        JsonObjectBuilder idWService=Json.createObjectBuilder();
        JsonObjectBuilder idWScenarioRecord=Json.createObjectBuilder();

        jsonObjectBuilder.add("id",id);
        jsonObjectBuilder.add("name",name);
        jsonObjectBuilder.add("cron",cron);

        for (WebServiceScenario webService : webServicesScenario) {
            jsonObjectBuilder.add("rang",webService.getRang());

            idWService.add("id",webService.getWebService().getId());
            idWService.add("rang",webService.getRang());
            idWService.add("name",webService.getWebService().getName());

            webserviceArray.add(idWService);
        }
        jsonObjectBuilder.add("webServices",webserviceArray);

        return jsonObjectBuilder;
    }
}