package com.xuhao.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class AdminController {

    @FXML
    private Button studentMgr;

    @FXML
    private Button courseMgr;

    @FXML
    private Button teacherMgr;

    @FXML
    private AnchorPane paneToReplace;

    @FXML
    protected void studentMgrBtnAction(ActionEvent event) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/xuhao/admin/student/StudentPanel.fxml"));
            paneToReplace.getChildren().setAll(root);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void courseMgrBtnAction(ActionEvent event) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/xuhao/admin/course/CoursePanel.fxml"));
            paneToReplace.getChildren().setAll(root);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    protected void teacherMgrBtnAction(ActionEvent event) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/xuhao/admin/teacher/TeacherPanel.fxml"));
            paneToReplace.getChildren().setAll(root);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
