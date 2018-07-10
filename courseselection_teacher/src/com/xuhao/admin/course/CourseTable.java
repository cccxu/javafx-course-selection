package com.xuhao.admin.course;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CourseTable {
    private StringProperty cId = new SimpleStringProperty();
    private StringProperty cName = new SimpleStringProperty();
    private StringProperty ctName = new SimpleStringProperty();
    private StringProperty cPoint = new SimpleStringProperty();

    public CourseTable(String id, String name, String tname, String point){
        setcId(id);
        setcName(name);
        setCtName(tname);
        setcPoint(point);
    }

    private void setcId(String id){
        this.cId.set(id);
    }
    private void setcName(String name){
        this.cName.set(name);
    }
    private void setCtName(String tname){
        this.ctName.set(tname);
    }
    private void setcPoint(String point){
        this.cPoint.set(point);
    }
    public String getCId(){
        return cId.get();
    }
    public  String getCNmae(){
        return cName.get();
    }
    public String getCTName(){
        return ctName.get();
    }
    public String getCPoint(){
        return cPoint.get();
    }
    public StringProperty cIdProperty(){
        return cId;
    }
    public StringProperty cNameProperty(){
        return cName;
    }
    public StringProperty ctNameProperty(){
        return ctName;
    }
    public StringProperty cPointProperty(){
        return cPoint;
    }
}
