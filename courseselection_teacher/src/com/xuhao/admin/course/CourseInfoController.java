package com.xuhao.admin.course;

import com.xuhao.sql.Sql;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class CourseInfoController {

    private CourseController father; //上级窗口指针，用于获取observablelist

    private String id;
    private String name;
    private String tid;
    private String point;

    //4 个输入框
    @FXML
    private TextField idText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField tidText;
    @FXML
    private TextField pointText;

    @FXML
    private Button cancelBtn;

    //StudentController通过这个函数获取数据
    public String[] getInfo(){
        String [] info = new String[4];
        info[0] = id;
        info[1] = name;
        info[2] = tid;
        info[3] = point;
        return info;
    }

    @FXML
    protected void okBtnAction(ActionEvent event){
        id = idText.getText();
        name = nameText.getText();
        tid = tidText.getText();
        point = pointText.getText();

        Sql sql = new Sql();
        sql.changeCourse(id, name, tid, point);
        try{
            ResultSet set = sql.getAllCourse();
            ObservableList<CourseTable> theTable;
            theTable = father.getCourseTable();
            theTable.clear();
            while(set.next()){
                theTable.add(new CourseTable(set.getString("course.cid"), set.getString("course.cname"),
                        set.getString("teacher.tname"), set.getString("course.point")));
            }
            //关闭rs后无法通过rs获得statement和connection，所以直接关connection，会自动关闭另两个
            set.getStatement().getConnection().close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    protected void clearBtnAction(ActionEvent event){
        idText.clear();
        nameText.clear();
        tidText.clear();
        pointText.clear();
    }
    @FXML
    protected void cancelBtnAction(ActionEvent event){
        Stage stage2 = (Stage) cancelBtn.getScene().getWindow();
        stage2.close();
    }

    public void setFather(CourseController pointer){
        father = pointer;
    }
}
