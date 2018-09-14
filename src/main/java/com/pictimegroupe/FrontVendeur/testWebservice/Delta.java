package com.pictimegroupe.FrontVendeur.testWebservice;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.util.UUID;

@Entity
public class Delta {

  @Id
  private String id= UUID.randomUUID().toString();
  @OneToOne
  private ServiceRecord serviceRecord;
  private String node;
  private String expctedValue;
  private String registeredValue;

    public Delta() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceRecord getServiceRecord() {
        return serviceRecord;
    }

    public void setServiceRecord(ServiceRecord serviceRecord) {
        this.serviceRecord = serviceRecord;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getExpctedValue() {
        return expctedValue;
    }

    public void setExpctedValue(String expctedValue) {
        this.expctedValue = expctedValue;
    }

    public String getRegisteredValue() {
        return registeredValue;
    }

    public void setRegisteredValue(String registeredValue) {
        this.registeredValue = registeredValue;
    }

    public JsonObjectBuilder getDeltoJson(){
        JsonObjectBuilder jsonObjectBuilder= Json.createObjectBuilder();
        jsonObjectBuilder.add("id",id);
        jsonObjectBuilder.add("node",node);
        jsonObjectBuilder.add("expctedValue",expctedValue);
        jsonObjectBuilder.add("registeredValue", registeredValue);
        jsonObjectBuilder.add("serviceRecordId",serviceRecord.getWebService().getName());
        return jsonObjectBuilder;
    }
}
