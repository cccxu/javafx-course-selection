package com.xuhao.admin.student;

import com.xuhao.admin.teacher.TeacherTable;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class StudentController {
    @FXML
    private TableView<StudentTable> sTable;
    @FXML
    private TableColumn<StudentTable, String> sId;
    @FXML
    private  TableColumn<StudentTable, String> sName;
    @FXML
    private TableColumn<StudentTable, String> sGender;
    @FXML
    private TableColumn<StudentTable, String> sMajor;
    @FXML
    private TableColumn<StudentTable, String> sYear;

    @FXML
    private TextField searchText;

    private String id;

    private ObservableList<StudentTable> studentTable = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        sId.setCellValueFactory(cellData -> cellData.getValue().sIdProperty());
        sName.setCellValueFactory(cellData -> cellData.getValue().sNameProperty());
        sGender.setCellValueFactory(cellData -> cellData.getValue().sGenderProperty());
        sMajor.setCellValueFactory(cellData -> cellData.getValue().sMajorProperty());
        sYear.setCellValueFactory(cellData -> cellData.getValue().sYearProperty());

        sTable.setItems(studentTable);

        try{
            Sql sql = new Sql();
            ResultSet set = sql.getAllStudent();
            while(set.next()){
                String gender;
                if(set.getString("gender").equals("1")){
                    gender = "男";
                }else{
                    gender = "女";
                }
                studentTable.add(new StudentTable(set.getString("sid"),set.getString("sname"),
                        gender,set.getString("major"),
                        set.getString("year")));
            }
            //关闭rs后无法通过rs获得statement和connection，所以直接关connection，会自动关闭另两个
            set.getStatement().getConnection().close();
        }catch(Exception e){
            e.printStackTrace();
        }

        sTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<StudentTable>() {
                    @Override
                    public void changed(ObservableValue<? extends StudentTable> observable,
                                        StudentTable oldValue, StudentTable newValue) {
                        if(newValue != null){
                            id = newValue.getSId();
                        }else{
                            id = null;
                        }
                    }
                }
        );
    }

    @FXML
    protected void tSearchBtnAction(ActionEvent event) throws Exception{
        try {
            Sql sql = new Sql();
            ResultSet rs = sql.getAllStudent();
            while (rs.next()) {
                if(rs.getString("sname").equals(searchText.getText()) ||
                        rs.getString("sid").equals(searchText.getText())){
                    studentTable.clear();
                    String gender;
                    if(rs.getString("gender").equals("1")){
                        gender = "男";
                    }else{
                        gender = "女";
                    }
                    studentTable.add(new StudentTable(rs.getString("sid"),rs.getString("sname"),
                            gender,rs.getString("major"),
                            rs.getString("year")));
                }
            }
            rs.getStatement().getConnection().close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void changeStudentBtnAction(ActionEvent event) throws Exception{
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentInfo.fxml"));
            Parent root = loader.load();
            StudentInfoController son;
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

    @FXML
    protected void deleteBtnAction(ActionEvent event) throws Exception{
        String gender;
        if(id == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("请选择要删除的条目");
        }else {
            Sql sql = new Sql();
            //删除相关语句
            sql.deleteStudent(id);
            //刷新列表
            studentTable.clear();
            try {
                ResultSet set = sql.getAllStudent();
                while (set.next()) {
                    if (set.getString("gender").equals("1")) {
                        gender = "男";
                    } else {
                        gender = "女";
                    }
                    studentTable.add(new StudentTable(set.getString("sid"), set.getString("sname"),
                            gender, set.getString("major"),
                            set.getString("year")));
                }
                set.getStatement().getConnection().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ObservableList<StudentTable> getStudentTable(){ return studentTable; }
}
