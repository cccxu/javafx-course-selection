package com.xuhao.student;

import com.xuhao.sql.Sql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.ResultSet;

public class StudentPageController {

    private int studentid;
    private String courseid;

    @FXML
    private TableView<StudentTable> table;
    @FXML
    private TableColumn<StudentTable, String> id;
    @FXML
    private TableColumn<StudentTable, String> name;
    @FXML
    private TableColumn<StudentTable, String> teacher;
    @FXML
    private TableColumn<StudentTable, String> point;

    @FXML
    private Text theId;

    @FXML
    private TextField idText;

    private ObservableList<StudentTable> sTable = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        id.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        teacher.setCellValueFactory(cellData -> cellData.getValue().teacherProperty());
        point.setCellValueFactory(cellData -> cellData.getValue().pointProperty());

        table.setItems(sTable);

        //添加监听器
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue != null){
                        courseid = newValue.getId();
                    }else{
                        courseid = null;
                    }
                }
        );
    }

    @FXML
    private void showCourse(ActionEvent event){
        Sql sql = new Sql();
        try {
            ResultSet rs = sql.getTakenCourse(String.valueOf(studentid));
            sTable.clear();
            while(rs.next()){
                sTable.add(new StudentTable(rs.getString("course.cid"),
                        rs.getString("course.cname"), rs.getString("teacher.tname"),
                        rs.getString("course.point")));
            }
            rs.getStatement().getConnection().close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void searchCourse(ActionEvent event){
        Sql sql = new Sql();
        ResultSet rs = sql.getAllCourse();
        try{
            while(rs.next()){
                if (rs.getString("course.cid").equals(idText.getText())){
                    sTable.clear();
                    sTable.add(new StudentTable(rs.getString("course.cid"),
                            rs.getString("course.cname"), rs.getString("teacher.tname"),
                            rs.getString("course.point")));
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void takeCourse(ActionEvent event){
        Sql sql = new Sql();
        sql.takeCourse(String.valueOf(studentid), idText.getText());
        sTable.clear();
        //刷新列表
        try {
            ResultSet rs = sql.getTakenCourse(String.valueOf(studentid));
            while(rs.next()){
                sTable.add(new StudentTable(rs.getString("course.cid"),
                        rs.getString("course.cname"), rs.getString("teacher.tname"),
                        rs.getString("course.point")));
            }
            rs.getStatement().getConnection().close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void cancelCourse(ActionEvent event){
        Sql sql = new Sql();
        sql.cancelCourse(String.valueOf(studentid), courseid);
        //刷新列表
        try {
            sTable.clear();
            ResultSet rs = sql.getTakenCourse(String.valueOf(studentid));
            while(rs.next()){
                sTable.add(new StudentTable(rs.getString("course.cid"),
                        rs.getString("course.cname"), rs.getString("teacher.tname"),
                        rs.getString("course.point")));
            }
            rs.getStatement().getConnection().close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setStudentid(int studentid){
        this.studentid = studentid;
        this.theId.setText("欢迎： "+String.valueOf(studentid)+" 同学");
    }
}
