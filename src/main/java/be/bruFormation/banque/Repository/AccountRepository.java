package be.bruFormation.banque.Repository;

import be.bruFormation.banque.models.Account;
import be.bruFormation.banque.models.CurrentAccount;
import be.bruFormation.banque.models.SaveAccount;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository extends Repository {

    @Override
    protected Account fromResultSet(ResultSet resultSet) throws SQLException {
        HolderRepository holderRepository = new HolderRepository();
        Account account;
        String number = resultSet.getString("number");
        int holder_id = resultSet.getInt("holder_id");
        double solde = resultSet.getDouble("solde");
        String desc = resultSet.getString("desc");
        if (desc.equals("CURRENT")) {
            double creditLine = resultSet.getDouble("credit_line");
            account = new CurrentAccount(number, holderRepository.findHolderById(holder_id), solde);
        } else {
            Date date = resultSet.getDate("last_whithdrawan_date");
            LocalDate dateLastWithdrawn = date.toLocalDate();
            account = new SaveAccount(number, holderRepository.findHolderById(holder_id), solde,dateLastWithdrawn);
        }
        return  account;
    }
    public List<Account> findAllAccount() throws SQLException {
        super.open();
        List<Account> accountList = new ArrayList<>();
        PreparedStatement statement = super.preparedStatement("SELECT * FROM Account");
        ResultSet resultSet = super.executeQuery(statement);
        while (resultSet.next()) {
            accountList.add(this.fromResultSet(resultSet));
        }
        super.close();
        return accountList;
    }
    public Account findAccountById(int account_id) throws SQLException {
        super.open();
        PreparedStatement statement = super.preparedStatement("SELECT * FROM Account WHERE ?");
        statement.setInt(1,account_id);
        ResultSet resultSet = super.executeQuery(statement);
        super.close();
        return this.fromResultSet(resultSet);
    }
    public void addAccount(Account account) throws SQLException {
        super.open();
//        String query = "INSERT INTO Account value((SELECT Max(student_id) FROM student)+1,?,?,?,?,?,?)";
//        PreparedStatement statement = super.preparedStatement(query);
//        statement.setString(1,account.getNumber());
//        statement.setDouble(2,account.getSolde());
//        statement.setInt(6, null);
//        statement.setInt(7, null);
        if (account instanceof CurrentAccount ){
            this.addCurrentAccount((CurrentAccount) account);
        }else {
            this.addSaveAccount((SaveAccount) account);
        }
        super.close();
    }
    private void addCurrentAccount(CurrentAccount account) throws SQLException {
        super.open();
        String query = "INSERT INTO Account value((SELECT Max(student_id) FROM student)+1,?,?,?,NULL,CURRENT,?,?)";
        PreparedStatement statement = super.preparedStatement(query);
        statement.setString(1,account.getNumber());
        statement.setDouble(2,account.getSolde());
        statement.setDouble(3,account.getCreditLine());
        statement.setInt(4, null);
        statement.setInt(5, null);
        // TODO getHolderID in HolderRepository
        // TODO getBankID in BankRepository
        ResultSet resultSet = super.executeQuery(statement);
        super.close();
    }
    private void addSaveAccount(SaveAccount account) throws SQLException {
        super.open();
        String query = "INSERT INTO Account value((SELECT Max(student_id) FROM student)+1,?,?,NULL,?,SAVING,?,?)";
        PreparedStatement statement = super.preparedStatement(query);
        statement.setString(1,account.getNumber());
        statement.setDouble(2,account.getSolde());
        statement.setDate(3, Date.valueOf(account.getLastWithdraw()));
        statement.setInt(4, null);
        statement.setInt(5, null);
        ResultSet resultSet = super.executeQuery(statement);
        // TODO getHolderID in HolderRepository
        // TODO getBankID in BankRepository
        super.close();
    }
}
