package com.pictimegroupe.FrontVendeur.testWebservice;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class WebServiceScenario implements Serializable {

    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_WEB_SERVICE")
    private WebService webService;

    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_SCENARIO")
    private Scenario scenario;
    @Id
    private int rang;



    public WebServiceScenario() {
    }


    public WebServiceScenario(WebService webService, Scenario scenario, int rang) {
        this.webService = webService;
        this.scenario = scenario;
        this.rang = rang;
    }

    public WebService getWebService() {
        return webService;
    }

    public void setWebService(WebService webService) {
        this.webService = webService;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }
}
