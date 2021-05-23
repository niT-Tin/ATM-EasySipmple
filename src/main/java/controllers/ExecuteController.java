package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import dao.AccountOperator;
import dao.Transfer;

public class ExecuteController {

    @FXML
    private TextField TarAccount;
    @FXML
    private TextField Amount;
    @FXML
    private TextArea ShowArea;
    
    AccountOperator accountOperator = new AccountOperator();

    public ExecuteController() throws Exception {
    }

    /**
     * deposit the money into designed account and
     * persistent data to the database
     * @throws Exception
     */
    @FXML
    public void depositAction() throws Exception {
        if(accountOperator.deposit(Double.parseDouble(Amount.getText()), false))
            new Alert(Alert.AlertType.INFORMATION, "存款成功",
                    new ButtonType("确定", ButtonBar.ButtonData.YES)).show();
        else
            new Alert(Alert.AlertType.INFORMATION, "存款失败",
                    new ButtonType("确定", ButtonBar.ButtonData.YES)).show();
        ShowArea.clear();
        ShowArea.setText(accountOperator.PackageInfo().toString());
    }


    /**
     * withdraw the money into designed account and
     * persistent data to the database
     * @throws Exception
     */
    @FXML
    public void withdrawAction() throws Exception {
        if(accountOperator.withDraw(Double.parseDouble(Amount.getText())))
            new Alert(Alert.AlertType.INFORMATION, "取款成功",
                    new ButtonType("确定", ButtonBar.ButtonData.YES)).show();
        else
            new Alert(Alert.AlertType.INFORMATION, "取款失败",
                    new ButtonType("确定", ButtonBar.ButtonData.YES)).show();
        ShowArea.clear();
        ShowArea.setText(accountOperator.PackageInfo().toString());
    }


    /**
     * transfer the money into designed account and
     * persistent data to the database
     * @throws Exception
     */
    @FXML
    public void transferAction() throws Exception {
        int tarAccount = Integer.parseInt(TarAccount.getText());
        double amount = Double.parseDouble(Amount.getText());
        Transfer tf = new Transfer();
        if(tf.transfer(tarAccount, amount))
            new Alert(Alert.AlertType.INFORMATION, "转账成功",
                    new ButtonType("确定", ButtonBar.ButtonData.YES)).show();
        else
            new Alert(Alert.AlertType.INFORMATION, "转账失败",
                    new ButtonType("确定", ButtonBar.ButtonData.YES)).show();
        ShowArea.clear();
        ShowArea.setText(accountOperator.PackageInfo().toString());
    }

    @FXML
    public void queryAction() throws Exception{
        ShowArea.clear();
        ShowArea.setText(accountOperator.PackageInfo().toString());
    }

}
