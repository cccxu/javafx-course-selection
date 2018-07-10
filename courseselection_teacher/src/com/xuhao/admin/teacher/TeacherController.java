package com.xuhao.admin.teacher;

import com.xuhao.sql.Sql;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.ResultSet;


public class TeacherController {

    @FXML
    private TableView<TeacherTable> tTable;
    @FXML
    private TableColumn<TeacherTable,String> tId;
    @FXML
    private TableColumn<TeacherTable, String> tName;

    @FXML
    private TextField tSearchText;

    private String id;

    private ObservableList<TeacherTable> teacherTable = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        tId.setCellValueFactory(cellData -> cellData.getValue().tIdProperty());
        tName.setCellValueFactory(cellData -> cellData.getValue().tNameProperty());

        tTable.setItems(teacherTable);

        try {
            Sql sql = new Sql();
            ResultSet rs = sql.getAllTeacher();
            while (rs.next()) {
                teacherTable.add(new TeacherTable(rs.getString("tid"), rs.getString("tname")));
            }
            rs.getStatement().getConnection().close();
        }catch(Exception e){
            e.printStackTrace();
        }

        tTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<TeacherTable>() {
                    @Override
                    public void changed(ObservableValue<? extends TeacherTable> observable, TeacherTable oldValue, TeacherTable newValue) {
                        if(newValue!= null) {
                            id = newValue.getCId();
                        }else{
                            id = null;
                        }
                    }
                }
        );
    }

    @FXML
    protected void deleteBtnAction(ActionEvent event) throws Exception{
        if(id == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("请选择要删除的条目");
        }else{
            //执行删除语句
            Sql sql = new Sql();
            sql.deleteTeacher(id);

            //刷新列表
            teacherTable.clear();
            try {
                ResultSet rs = sql.getAllTeacher();
                while (rs.next()) {
                    teacherTable.add(new TeacherTable(rs.getString("tid"), rs.getString("tname")));
                }
                rs.getStatement().getConnection().close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void teacherInfoBtnAction(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherInfo.fxml"));
        Parent root = loader.load();
        TeacherInfoController son;
        son = loader.getController();
        son.setFather(this);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    protected void tSearchBtnAction(ActionEvent event) throws Exception{
        teacherTable.clear();
        try {
            Sql sql = new Sql();
            ResultSet rs = sql.getAllTeacher();
            while (rs.next()) {
                if(rs.getString("tname").equals(tSearchText.getText()) || rs.getString("tid").equals(tSearchText.getText())){
                    teacherTable.add(new TeacherTable(rs.getString("tid"), rs.getString("tname")));
                }
            }
            rs.getStatement().getConnection().close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<TeacherTable> getTeacherTable(){
        return teacherTable;
    }


}
