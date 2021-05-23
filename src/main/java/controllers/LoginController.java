package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import dao.AccountOperator;
import utils.StageUtils;
import Myenum.PageStatus;

public class LoginController {

    @FXML
    TextField accountField;
    @FXML
    PasswordField passwdField;
    AccountOperator accountOperator = new AccountOperator();
    PageStatus status;
    int accountId;

    public LoginController() throws Exception {
    }

    @FXML
    public void loginAction() throws Exception {
       try{
           accountOperator.setAccount(Integer.parseInt(accountField.getText()));
           accountOperator.setPassword(Integer.parseInt(passwdField.getText()));
           accountId = Integer.parseInt(accountField.getText());
           if(accountOperator.isExist(accountOperator.getAccount(),
                   accountOperator.getPassword())){
               StageUtils.getCurrentStage().close();
               status = PageStatus.LOGINED;
               StageUtils.setStage(StageUtils.getMap().get(status));
           }
           else{
               new Alert(Alert.AlertType.ERROR, "输入账号或密码错误"
                       ,new ButtonType("确定", ButtonBar.ButtonData.OK_DONE)).show();
           }
       }catch (NumberFormatException nfe){
           new Alert(Alert.AlertType.ERROR, "输入账号或密码错误"
                   ,new ButtonType("确定", ButtonBar.ButtonData.OK_DONE)).show();
       }

    }

    @FXML
    public void logoutAction(){
        System.exit(0);
    }

}
