package com.krawa.estafetatest.model;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable{

//{"Id":124906,"Number":"2012/619199","PlannedStartDate":null,"PlannedEndDate":null,"ActualStartDate":"2012-03-23T07:30:00Z",
// "ActualEndDate":"2012-03-23T07:30:00Z","Vin":"VF1CRJN0646757203","Model":"Clio","ModelCode":"CRJN06","Brand":"Renault",
// "SurveyPoint":"Avtoleader-M","Carrier":"KATP 13061 PJSC","Driver":"Chornoostrovskyj Yuriy Petrovych"}

    private int Id;
    private String Number;
    private Date PlannedStartDate;
    private Date PlannedEndDate;
    private Date ActualStartDate;
    private Date ActualEndDate;
    private String Vin;
    private String Model;
    private String ModelCode;
    private String Brand;
    private String SurveyPoint;
    private String Carrier;
    private String Driver;

    public int getId() {
        return Id;
    }

    public String getNumber() {
        return Number;
    }

    public Date getPlannedStartDate() {
        return PlannedStartDate;
    }

    public Date getPlannedEndDate() {
        return PlannedEndDate;
    }

    public Date getActualStartDate() {
        return ActualStartDate;
    }

    public Date getActualEndDate() {
        return ActualEndDate;
    }

    public String getVin() {
        return Vin;
    }

    public String getModel() {
        return Model;
    }

    public String getModelCode() {
        return ModelCode;
    }

    public String getBrand() {
        return Brand;
    }

    public String getSurveyPoint() {
        return SurveyPoint;
    }

    public String getCarrier() {
        return Carrier;
    }

    public String getDriver() {
        return Driver;
    }
}
