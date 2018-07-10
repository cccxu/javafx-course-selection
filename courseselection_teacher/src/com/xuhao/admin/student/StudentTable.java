package com.xuhao.admin.student;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentTable {
    private StringProperty sId = new SimpleStringProperty();
    private StringProperty sName = new SimpleStringProperty();
    private StringProperty sGender = new SimpleStringProperty();
    private StringProperty sMajor = new SimpleStringProperty();
    private StringProperty sYear = new SimpleStringProperty();

    public StudentTable(String id, String name, String gender, String major, String year){
        setsId(id);
        setsName(name);
        setsGender(gender);
        setsMajor(major);
        setsYear(year);
    }

    private void setsId(String id){
        this.sId.set(id);
    }
    private void setsName(String name){
        this.sName.set(name);
    }
    private void setsGender(String Gender){
        this.sGender.set(Gender);
    }
    private void setsMajor(String Major){
        this.sMajor.set(Major);
    }
    private void setsYear(String year){
        this.sYear.set(year);
    }
    public String getSId(){
        return sId.get();
    }
    public  String getSNmae(){
        return sName.get();
    }
    public String getSGender(){
        return sGender.get();
    }
    public String getSMajor(){
        return sMajor.get();
    }
    public String getSYear(){
        return sYear.get();
    }
    public StringProperty sIdProperty(){
        return sId;
    }
    public StringProperty sNameProperty(){
        return sName;
    }
    public StringProperty sGenderProperty(){
        return sGender;
    }
    public StringProperty sMajorProperty(){
        return sMajor;
    }
    public StringProperty sYearProperty(){
        return sYear;
    }
}
