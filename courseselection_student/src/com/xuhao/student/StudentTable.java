package com.xuhao.student;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentTable {
    private StringProperty id = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty teacher = new SimpleStringProperty();
    private StringProperty point = new SimpleStringProperty();

    public StudentTable(String id, String name, String teacher, String point){
        setId(id);
        setName(name);
        setTeacher(teacher);
        setPoint(point);
    }

    private void setId(String id){
        this.id.set(id);
    }
    private void setName(String name){
        this.name.set(name);
    }
    private void setTeacher(String teacher){
        this.teacher.set(teacher);
    }
    private void setPoint(String point){
        this.point.set(point);
    }

    protected String getId(){
        return id.get();
    }
    protected String getName(){
        return name.get();
    }
    protected String getTeacher(){
        return teacher.get();
    }
    protected String getPoint(){
        return point.get();
    }

    protected StringProperty idProperty(){
        return id;
    }
    protected StringProperty nameProperty(){
        return name;
    }
    protected StringProperty teacherProperty(){
        return teacher;
    }
    protected StringProperty pointProperty(){
        return point;
    }
}
