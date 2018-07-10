package com.xuhao.student;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudentPage extends Application {

    private Stage stage = new Stage();
    private int sid;

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentPage.fxml"));
        Parent root = loader.load();
        StudentPageController student = loader.<StudentPageController>getController();
        student.setStudentid(sid);
        stage.setTitle("学生主界面");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

    public void showWindow() throws Exception{
        start(stage);
    }

    public void setSid(int id){
        sid = id;
    }

}
