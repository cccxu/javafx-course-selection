package com.xuhao.admin.teacher;

import com.xuhao.sql.Sql;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class TeacherInfoController {
    private String id;
    private String name;
    private TeacherController father;
    private ObservableList<TeacherTable> theTable;

    @FXML
    private Button ok;
    @FXML
    private Button clear;
    @FXML
    private Button cancel;

    @FXML
    private TextField idText;
    @FXML
    private TextField nameText;

    @FXML
    protected void okBtnAction(ActionEvent event){
        //添加教师信息 || 修改教师信息
        id = idText.getText();
        name = nameText.getText();
        theTable = father.getTeacherTable();
        //执行SQL
        Sql sql = new Sql();
        sql.changeTeacher(id, name);
        //刷新列表
        theTable.clear();
        try {
            ResultSet rs = sql.getAllTeacher();
            while (rs.next()) {
                theTable.add(new TeacherTable(rs.getString("tid"), rs.getString("tname")));
            }
            rs.getStatement().getConnection().close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void clearBtnAction(ActionEvent event){
        idText.clear();
        nameText.clear();
    }

    @FXML
    protected  void cancelBtnAction(ActionEvent event){
        Stage stage2 = (Stage) cancel.getScene().getWindow();
        stage2.close();
    }

    public void setFather(TeacherController pointer){
        father = pointer;
    }
}
