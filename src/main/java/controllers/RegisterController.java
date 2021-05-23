package controllers;

import Myenum.PageStatus;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import dao.AccountOperator;
import utils.StageUtils;

public class RegisterController {
    @FXML
    private TextField rTextField;
    @FXML
    private PasswordField rPwdField;

    AccountOperator accountOperator = new AccountOperator();
    PageStatus status;

    public RegisterController() throws Exception {
    }

    @FXML
    public void toLogin(){
        StageUtils.getCurrentStage().close();
        StageUtils.setStage(StageUtils.getMap().get(PageStatus.REGISTERED));
    }

    /**
     *register for new customers
     */
    @FXML
    public void Register(){
        try{
            accountOperator.setAccount(Integer.parseInt(rTextField.getText()));
            accountOperator.setPassword(Integer.parseInt(rPwdField.getText()));
            if(!accountOperator.isExist(accountOperator.getAccount(),
                    accountOperator.getPassword())){
                if(accountOperator.register()){
                    new Alert(Alert.AlertType.ERROR, "注册成功"
                            ,new ButtonType("确定", ButtonBar.ButtonData.OK_DONE)).show();
                    status = PageStatus.REGISTERED;
                    StageUtils.getCurrentStage().close();
                    StageUtils.setStage(StageUtils.getMap().get(status));
                }else
                    new Alert(Alert.AlertType.ERROR, "账号已存在"
                            ,new ButtonType("确定", ButtonBar.ButtonData.OK_DONE)).show();
            }else
                new Alert(Alert.AlertType.ERROR, "账号已存在"
                        ,new ButtonType("确定", ButtonBar.ButtonData.OK_DONE)).show();
        }catch(NumberFormatException nfe){
            new Alert(Alert.AlertType.ERROR, "输入账号或密码错误"
                    ,new ButtonType("确定", ButtonBar.ButtonData.OK_DONE)).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
