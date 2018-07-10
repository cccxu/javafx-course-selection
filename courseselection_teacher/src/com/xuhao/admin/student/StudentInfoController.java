package com.xuhao.admin.student;

import com.xuhao.sql.Sql;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class StudentInfoController {

    private StudentController father; //上级窗口指针，用于获取observablelist

    private String id;
    private String name;
    private String gender;  //1: male; 0； female
    private String major;
    private String year;

    //5 个输入框
    @FXML
    private TextField idText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField genderText;
    @FXML
    private TextField majorText;
    @FXML
    private TextField yearText;

    @FXML
    private Button cancelBtn;

    private ObservableList<StudentTable> theTable;

    //StudentController通过这个函数获取数据
    public String[] getInfo(){
        String [] info = new String[5];
        info[0] = id;
        info[1] = name;
        info[2] = gender;
        info[3] = major;
        info[4] = year;
        return info;
    }

    @FXML
    protected void okBtnAction(ActionEvent event){
        id = idText.getText();
        name = nameText.getText();
        gender = genderText.getText();
        major = majorText.getText();
        year = yearText.getText();
        theTable = father.getStudentTable();

        String sex = null;
        if(gender.equals("男")){
            sex = "1";
        }else if(gender.equals("女")){
            sex = "0";
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("请输入有效的性别（男/女）");
        }
        Sql sql = new Sql();
        sql.changeStudent(id, name, sex, major, year);
        theTable.clear();
        try{
            ResultSet set = sql.getAllStudent();
            while(set.next()){
                String gender;
                if(set.getString("gender").equals("1")){
                    gender = "男";
                }else{
                    gender = "女";
                }
                theTable.add(new StudentTable(set.getString("sid"),set.getString("sname"),
                        gender,set.getString("major"),
                        set.getString("year")));
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
        genderText.clear();
        yearText.clear();
        majorText.clear();
    }
    @FXML
    protected void cancelBtnAction(ActionEvent event){
        Stage stage2 = (Stage) cancelBtn.getScene().getWindow();
        stage2.close();
    }

    public void setFather(StudentController pointer){
        father = pointer;
    }
}
