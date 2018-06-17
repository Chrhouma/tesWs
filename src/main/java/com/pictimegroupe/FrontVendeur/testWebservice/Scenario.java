package com.pictimegroupe.FrontVendeur.testWebservice;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

   // private Integer rang;

    @ManyToMany
    @Cascade({CascadeType.ALL})
    @JoinTable(name = "WebService_Scenario",
                joinColumns = { @JoinColumn(name = "idScenario", referencedColumnName = "id")},
                inverseJoinColumns = { @JoinColumn(name = "idWebService",referencedColumnName = "id"),
                        @JoinColumn(name = "rang",referencedColumnName = "rang")})
    private List<WebService> webServices= new LinkedList<WebService>();


    public Scenario() {
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

   public List<WebService> getWebServices() {
        return webServices;
    }

    public void setWebServices(List<WebService> webServices) {
        this.webServices = webServices;
    }



    public JsonObjectBuilder getScenarioJson() {

        JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();
        JsonArrayBuilder webserviceArray =Json.createArrayBuilder();
        JsonObjectBuilder idWService=Json.createObjectBuilder();
        JsonObjectBuilder rangWebService=Json.createObjectBuilder();
        JsonArrayBuilder webserviceinfoArray =Json.createArrayBuilder();

        jsonObjectBuilder.add("id",id);
        jsonObjectBuilder.add("name",name);
        jsonObjectBuilder.add("cron",cron);

        for (WebService webService : webServices) {
           jsonObjectBuilder.add("rang",webService.getRang());
            idWService.add("id",webService.getId());
            idWService.add("rang",webService.getRang());

            webserviceArray.add(idWService);
        }

        jsonObjectBuilder.add("webServices",webserviceArray);

        return jsonObjectBuilder;
    }
}
