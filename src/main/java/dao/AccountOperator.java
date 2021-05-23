package dao;

import utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * This class is for Account to CRUD
 */
@SuppressWarnings("all")
public class AccountOperator {

    private static int account;
    private static int password;
    private static int tarAccount;
    Connection conn = JdbcUtils.getConnection();

    public static int getTarAccount() {
        return tarAccount;
    }

    public static void setTarAccount(int tarAccount) {
        AccountOperator.tarAccount = tarAccount;
    }

    public AccountOperator() throws Exception {
    }

    /**
     *
     * @return the Account of String type
     */
    public int getAccount() {
        return account;
    }

    /**
     *
     * @param account
     * set the Account as the same as the parameter
     */
    public void setAccount(int account) {
        this.account = account;
    }

    /**
     *
     * @return the Password of String type
     */
    public int getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * set the password as the same as the parameter
     */
    public void setPassword(int password) {
        this.password = password;
    }

    /**
     *
     * @return the true or false whether the account is existed in
     * the Database
     * @throws Exception
     */
    public boolean isExist() throws Exception {
        if(account == 0)
            return false;
        PreparedStatement preparedStatement = conn.prepareStatement(
                "select * from users where `account`=?");
        preparedStatement.setInt(1, account);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    /**
     *
     * @return the true of false whether the both account and transfer account is
     * existed
     * @throws Exception
     */
    public boolean isExistTransfer() throws Exception{
        if(account == 0 && tarAccount == 0)
            return false;
        PreparedStatement preparedStatement = conn.prepareStatement(
                "select * from users where `account`=? or `account`=?");
        preparedStatement.setInt(1, account);
        preparedStatement.setInt(2, tarAccount);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.last();
        int fetchSize = resultSet.getRow();
        if(fetchSize == 2)
            return true;
        else
            return false;
    }

    public boolean isExist(int account, int password) throws Exception {
        if(account == 0)
            return false;
        PreparedStatement preparedStatement = conn.prepareStatement(
                "select * from users where `account`=?");
        preparedStatement.setInt(1, account);
//        preparedStatement.setInt(2,password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            if(resultSet.getInt("account") == account && resultSet.getInt("" +
                    "password") == password)
                return true;
            else
                return false;
        } else
            return false;
    }

    /**
     *
     * @return the true of false whether the registration is succeed
     * @throws Exception
     */
    public boolean register() throws Exception {
        if(account == 0 || password == 0)
            return false;
        String sql = "insert into users (`account`, `password`) values" +
                " (?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, account);
        preparedStatement.setInt(2, password);
        return preparedStatement.executeUpdate() > 0;
    }

    /**
     *
     * @param amount the money to transfer
     * @return true or false whether the balance is enough to transfer
     * @throws Exception
     */
    public boolean isEnough(double amount) throws Exception {
        String sql = "select balance from users where `account`=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, account);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        if(resultSet.next())
            return resultSet.getFloat("balance") > amount;
        return false;
    }

    /**
     *
     * @param money the money to be deposited
     * @param isTransfer set the situation whether is the transfer or not
     * @return true or false whether the action is succeed
     * @throws Exception
     */
    public boolean deposit(double money, boolean isTransfer) throws Exception {
        if(money <= 0 || account == 0)
            return false;
        int account = 0;
        if(isTransfer){
            account = this.tarAccount;
        }else
            account = this.account;
        PreparedStatement preparedStatement = conn.prepareStatement(
                "select `balance` from users where `account`=?"
        );
        preparedStatement.setInt(1, account);
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();
        if(resultSet.next()){
            double m = resultSet.getFloat("balance");
            PreparedStatement preparedStatement1 = conn.prepareStatement(
                    "update users set `balance`=? where account=?"
            );
            preparedStatement1.setDouble(1, money + m);
            preparedStatement1.setInt(2, account);
            preparedStatement1.execute();
            return true;
        }else
            return false;
    }

    /**
     *
     * @param money the money to be withdrawed
     * @return true or false whether the action is succeed
     * @throws Exception
     */
    public boolean withDraw(double money) throws Exception {
        if(account == 0)
            return false;
        PreparedStatement preparedStatement = conn.prepareStatement(
                "select `balance` from users where `account`=?"
        );
        preparedStatement.setInt(1, account);
        if(!preparedStatement.execute()) return false;
        ResultSet resultSet = preparedStatement.getResultSet();
        if(resultSet.next()){
            if(money > resultSet.getFloat("balance"))
                return false;
            else{
                preparedStatement = conn.prepareStatement(
                        "update users set `balance`=? where account=?"
                );
                preparedStatement.setInt(2, account);
                preparedStatement.setDouble(1, resultSet.getFloat("" +
                        "balance") - money);
                preparedStatement.executeUpdate();
                return true;
            }
        }else
            return false;
    }

    /**
     * Package the information from the method which named getInfo
     * @return A object of StringBuilder which contain the information of
     * designed account
     */
    public StringBuilder PackageInfo() throws Exception {
        ResultSet rs = getInfo();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%20s%20s\n","用户账号","用户余额\n"));
        while (rs.next()){
            stringBuilder.append(String.format("%25d%21d\n",
                    rs.getInt("account"),
                    rs.getInt("balance")));
        }
        return stringBuilder;
    }

    /**
     * get the designated information from the database
     * @return the RseultSet of information
     */
    private ResultSet getInfo() throws Exception {
        Connection conn = JdbcUtils.getConnection();
        String sql = "select * from " +
                "users where `account`=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, this.account);
        if(preparedStatement.execute())
            return preparedStatement.getResultSet();
        return null;
    }
}
