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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (Id != task.Id) return false;
        if (!Number.equals(task.Number)) return false;
        if (PlannedStartDate != null ? !PlannedStartDate.equals(task.PlannedStartDate) : task.PlannedStartDate != null)
            return false;
        if (PlannedEndDate != null ? !PlannedEndDate.equals(task.PlannedEndDate) : task.PlannedEndDate != null)
            return false;
        if (ActualStartDate != null ? !ActualStartDate.equals(task.ActualStartDate) : task.ActualStartDate != null)
            return false;
        if (ActualEndDate != null ? !ActualEndDate.equals(task.ActualEndDate) : task.ActualEndDate != null)
            return false;
        if (!Vin.equals(task.Vin)) return false;
        if (Model != null ? !Model.equals(task.Model) : task.Model != null) return false;
        if (ModelCode != null ? !ModelCode.equals(task.ModelCode) : task.ModelCode != null)
            return false;
        if (Brand != null ? !Brand.equals(task.Brand) : task.Brand != null) return false;
        if (SurveyPoint != null ? !SurveyPoint.equals(task.SurveyPoint) : task.SurveyPoint != null)
            return false;
        if (Carrier != null ? !Carrier.equals(task.Carrier) : task.Carrier != null) return false;
        return Driver != null ? Driver.equals(task.Driver) : task.Driver == null;

    }

    @Override
    public int hashCode() {
        int result = Id;
        result = 31 * result;
        result = 31 * result + (PlannedStartDate != null ? PlannedStartDate.hashCode() : 0);
        result = 31 * result + (PlannedEndDate != null ? PlannedEndDate.hashCode() : 0);
        result = 31 * result + (ActualStartDate != null ? ActualStartDate.hashCode() : 0);
        result = 31 * result + (ActualEndDate != null ? ActualEndDate.hashCode() : 0);
        result = 31 * result + Vin.hashCode();
        result = 31 * result + (Model != null ? Model.hashCode() : 0);
        result = 31 * result + (ModelCode != null ? ModelCode.hashCode() : 0);
        result = 31 * result + (Brand != null ? Brand.hashCode() : 0);
        result = 31 * result + (SurveyPoint != null ? SurveyPoint.hashCode() : 0);
        result = 31 * result + (Carrier != null ? Carrier.hashCode() : 0);
        result = 31 * result + (Driver != null ? Driver.hashCode() : 0);
        return result;
    }
}
