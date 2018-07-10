package com.xuhao.login;

import com.xuhao.sql.Sql;
import com.xuhao.student.StudentPage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javafx.event.ActionEvent;

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
            case 2:
                StudentPage stu = new StudentPage();
                try{
                    stu.setSid(Integer.parseInt(userId));
                    stu.showWindow();
                } catch(Exception e){
                    e.printStackTrace();
                }
                Stage stage2 = (Stage) loginBtn.getScene().getWindow();
                stage2.close();
                break;
        }
    }
}
