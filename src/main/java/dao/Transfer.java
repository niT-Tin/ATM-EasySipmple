package dao;


/**
 * Class for transfer
 */
@SuppressWarnings("all")
public class Transfer {

    AccountOperator srcAccountOperator;

    public Transfer() throws Exception {
        srcAccountOperator = new AccountOperator();
    }

    /**
     * @param srcAccount Source account to withdraw
     * @param tarAccount Target account to deposit
     * @param amount Amount
     * @return true or false whether the transfer is succeed
     * @throws Exception
     */
    public boolean transfer(int tarAccount, double amount) throws Exception {
        srcAccountOperator.setTarAccount(tarAccount);
        if(srcAccountOperator.isExistTransfer()){
            if(srcAccountOperator.isEnough(amount)){
                srcAccountOperator.withDraw(amount);
                srcAccountOperator.deposit(amount, true);
                return true;
            }
        }
        return false;
    }

}
