package com.pictimegroupe.FrontVendeur.testWebservice;

import org.hibernate.annotations.Cascade;
import javax.persistence.Entity;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Entity
public class WebService implements Serializable  {
    @Id
    private String id =UUID.randomUUID().toString();
    private String name;
    private String url;
    private String body;
    private Integer rang;
    private  String method;

    @OneToOne
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Schema inputSchema ;

    @OneToOne
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Schema outSchema ;

    @OneToMany(mappedBy="webService", cascade = CascadeType.ALL)
    private List<WebServiceScenario> webServicesScenario;

    public WebService() {
    }

    public List<WebServiceScenario> getWebServicesScenario() {
        return webServicesScenario;
    }

    public void setWebServicesScenario(List<WebServiceScenario> webServicesScenario) {
        this.webServicesScenario = webServicesScenario;
    }

    public WebService(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Schema getInputSchema() {
        return inputSchema;
    }

    public void setInputSchema(Schema inputSchema) {
        this.inputSchema = inputSchema;
    }

    public Schema getOutSchema() {
        return outSchema;
    }

    public void setOutSchema(Schema outSchema) {
        this.outSchema = outSchema;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRang() {
        return rang;
    }

    public void setRang(Integer rang) {
        this.rang = rang;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public JsonObjectBuilder getWebServiceJson() {

        JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();
        JsonObjectBuilder jsonObjectBuilderschemaInput= Json.createObjectBuilder();
        JsonObjectBuilder jsonObjectBuilderschemaOutput= Json.createObjectBuilder();
        jsonObjectBuilder.add("id",this.getId());
       jsonObjectBuilder.add("rang",this.getRang());

        jsonObjectBuilder.add("name",this.getName());
        jsonObjectBuilder.add("url",this.getUrl());
        jsonObjectBuilder.add("description",this.getDescription());
        jsonObjectBuilder.add("methode",this.getMethod());
        jsonObjectBuilder.add("body", this.getBody());
       /* if(this.getMethod().equals("post")) {
            jsonObjectBuilder.add("body", this.getBody());
        } else
         {
             jsonObjectBuilder.add("body","");
         }*/
        jsonObjectBuilderschemaInput.add("InputShemaName",this.getInputSchema().getName());
        jsonObjectBuilderschemaInput.add("InputShemapath",this.getInputSchema().getShemapath());

        jsonObjectBuilderschemaOutput.add("OutputSchemaName",this.getOutSchema().getName());
//        jsonObjectBuilderschemaOutput.add("Outputshemapath",this.getOutSchema().getShemapath());
        jsonObjectBuilder.add("InputShema",jsonObjectBuilderschemaInput);
        jsonObjectBuilder.add("OutputShema",jsonObjectBuilderschemaOutput);
        return jsonObjectBuilder;
    }
}
