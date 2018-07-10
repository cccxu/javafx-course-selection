package com.xuhao.admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Admin extends Application {

    private Stage stage = new Stage();
    private int tid;
    private String tname;

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));

        stage.setTitle("管理员主界面");
        stage.setScene(new Scene(root, 575, 400));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

    public void showWindow() throws Exception{
        start(stage);
    }

    public void setTid(int tid){
        tid = tid;
    }

    public void setTname(String tname){
        tname = tname;
    }
}
