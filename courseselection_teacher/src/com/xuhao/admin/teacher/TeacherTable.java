package com.xuhao.admin.teacher;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TeacherTable {
    private StringProperty tId = new SimpleStringProperty();
    private StringProperty tName = new SimpleStringProperty();

    public TeacherTable(String id, String name){
        settId(id);
        settName(name);
    }

    private void settId(String id){
        this.tId.set(id);
    }
    private void settName(String name){
        this.tName.set(name);
    }
    public String getCId(){
        return tId.get();
    }
    public  String getCNmae(){
        return tName.get();
    }
    public StringProperty tIdProperty(){
        return tId;
    }
    public StringProperty tNameProperty(){
        return tName;
    }
}
