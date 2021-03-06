package com.xuhao.login;

import com.xuhao.sql.Sql;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javafx.event.ActionEvent;

import com.xuhao.admin.Admin;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private Text actiontarget;

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField pwdTextField;

    @FXML
    private Button loginBtn;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event){
        String userId = new String();
        userId = userNameTextField.getText();

        String pwd = new String();
        pwd = pwdTextField.getText();


        Sql user = new Sql();
        int val = user.userChecker(userId , pwd);

        switch (val){
            case 0:
                actiontarget.setText("用户名或密码错误");
                break;
            case 1:
                Admin admin = new Admin();
                try {
                    admin.setTid(Integer.parseInt(userId));
                    admin.showWindow();
                } catch(Exception e){
                    e.printStackTrace();
                }
                Stage stage1 = (Stage) loginBtn.getScene().getWindow();
                stage1.close();
                break;
        }
    }
}
