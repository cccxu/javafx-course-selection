package com.xuhao.admin.course;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.xuhao.sql.Sql;

import java.sql.ResultSet;

public class CourseController {

    private String id;

    @FXML
    private TextField searchText;

    @FXML
    private TableView<CourseTable> cTable;
    @FXML
    private TableColumn<CourseTable, String> cId;
    @FXML
    private TableColumn<CourseTable, String> cName;
    @FXML
    private TableColumn<CourseTable, String> ctName;
    @FXML
    private TableColumn<CourseTable, String> cPoint;

    private ObservableList<CourseTable> courseTable = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        cId.setCellValueFactory(cellData -> cellData.getValue().cIdProperty());
        cName.setCellValueFactory(cellData -> cellData.getValue().cNameProperty());
        ctName.setCellValueFactory(cellData -> cellData.getValue().ctNameProperty());
        cPoint.setCellValueFactory(cellData -> cellData.getValue().cPointProperty());

        cTable.setItems(courseTable);

        try {
            ResultSet set;
            Sql sql = new Sql();
            set = sql.getAllCourse();
            while (set.next()) {
                courseTable.add(new CourseTable(set.getString("course.cid"), set.getString("course.cname"),
                        set.getString("teacher.tname"), set.getString("course.point")));
            }
            cTable.refresh();
            set.close();
        } catch(Exception e){
            e.printStackTrace();
        }

        cTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<CourseTable>() {
                    @Override
                    public void changed(ObservableValue<? extends CourseTable> observable, CourseTable oldValue, CourseTable newValue) {
                        if(newValue != null){
                            id = newValue.getCId();
                        }else{
                            id = null;
                        }
                    }
                }
        );
    }

    void CourseController(){
    }

    //删除课程
    @FXML
    protected void deleteBtnAction(ActionEvent event) throws Exception{
        String gender;
        if(id == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("请选择要删除的条目");
        }else {
            Sql sql = new Sql();
            //删除相关语句
            sql.deleteCourse(id);
            //刷新列表
            courseTable.clear();
            try {
                ResultSet set;
                set = sql.getAllCourse();
                while (set.next()) {
                    courseTable.add(new CourseTable(set.getString("course.cid"), set.getString("course.cname"),
                            set.getString("teacher.tname"), set.getString("course.point")));
                }
                cTable.refresh();
                set.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    //添加或修改课程面板
    @FXML
    protected void handlecBtnAddAction(Event event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseInfo.fxml"));
            Parent root = loader.load();
            CourseInfoController son;
            son = loader.getController();
            son.setFather(this);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //搜索功能
    @FXML
    protected void cSearchBtnAction(ActionEvent event) throws Exception{
        try {
            Sql sql = new Sql();
            ResultSet set = sql.getAllCourse();
            while (set.next()) {
                if(set.getString("cname").equals(searchText.getText()) ||
                        set.getString("cid").equals(searchText.getText())){
                    courseTable.clear();
                    courseTable.add(new CourseTable(set.getString("course.cid"), set.getString("course.cname"),
                            set.getString("teacher.tname"), set.getString("course.point")));
            }
            }
            set.getStatement().getConnection().close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<CourseTable> getCourseTable(){
        return courseTable;
    }
}

