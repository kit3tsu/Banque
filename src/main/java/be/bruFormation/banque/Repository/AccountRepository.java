package be.bruFormation.banque.Repository;

import be.bruFormation.banque.models.Account;
import be.bruFormation.banque.models.Bank;
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
        Bank bank = new Bank("..."); //TODO handle bank creation
        HolderRepository holderRepository = new HolderRepository();
        Account account;
        String number = resultSet.getString("number");
        int holderId = resultSet.getInt("holder_id");
        double solde = resultSet.getDouble("sold");
        String desc = resultSet.getString("desc");
        if (desc.equals("CURRENT")) {
            double creditLine = resultSet.getDouble("credit_line");
            account = bank.creatCurrentAccount(holderRepository.findHolderById(holderId), solde);
        } else {
            Date date = resultSet.getDate("last_withdrawn_date");
            LocalDate dateLastWithdrawn = date.toLocalDate();
            account = bank.creatSaveAccount(holderRepository.findHolderById(holderId), solde);
            ((SaveAccount) account).setDateLastWithdrawn(dateLastWithdrawn);
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
    public Account findAccountById(int accountId) throws SQLException {
        super.open();
        PreparedStatement statement = super.preparedStatement("SELECT * FROM Account WHERE ?");
        statement.setInt(1,accountId);
        ResultSet resultSet = super.executeQuery(statement);
        super.close();
        return this.fromResultSet(resultSet);
    }
    public void addAccount(Account account, String natNumber, String swift) throws SQLException {
        HolderRepository hr = new HolderRepository();
        BankRepository br = new BankRepository();
        int holderId =  hr.findHolderIdByNationalNumber(natNumber);
        int bankId = br.findBankIdBySwift(swift);
        if (account instanceof CurrentAccount ){
            this.addCurrentAccount((CurrentAccount) account,holderId,bankId);
        }else {
            this.addSaveAccount((SaveAccount) account,holderId,bankId);
        }
    }
    private void addCurrentAccount(CurrentAccount account, int holderId, int bankId) throws SQLException {
        HolderRepository hr = new HolderRepository();
        BankRepository br = new BankRepository();
        super.open();
        String query = "INSERT INTO Account value((SELECT Max(account_id) FROM Account)+1,?,?,?,NULL,CURRENT,?,?)";
        PreparedStatement statement = super.preparedStatement(query);
        statement.setString(1,account.getNumber());
        statement.setDouble(2,account.getSolde());
        statement.setDouble(3,account.getCreditLine());
        statement.setInt(4, holderId);
        statement.setInt(5, bankId);
        ResultSet resultSet = super.executeQuery(statement);
        super.close();
    }
    private void addSaveAccount(SaveAccount account, int holderId, int bankId) throws SQLException {
        super.open();
        String query = "INSERT INTO Account value((SELECT Max(account_id) FROM Account)+1,?,?,NULL,?,SAVING,?,?)";
        PreparedStatement statement = super.preparedStatement(query);
        statement.setString(1,account.getNumber());
        statement.setDouble(2,account.getSolde());
        statement.setDate(3, Date.valueOf(account.getLastWithdraw()));
        statement.setInt(4, holderId);
        statement.setInt(5, bankId);
        ResultSet resultSet = super.executeQuery(statement);
        super.close();
    }
    public List<Account> findAllAccountOfTheBankById(int bankId) throws SQLException {
        AccountRepository accountRepository = new AccountRepository();
        super.open();
        List<Account> accountList = new ArrayList<>();
        PreparedStatement statement = super.preparedStatement("SELECT * FROM Account WHERE bank_id = ?");
        statement.setInt(1,bankId);
        ResultSet resultSet = super.executeQuery(statement);
        while (resultSet.next()) {
            accountList.add(this.fromResultSet(resultSet));
        }
        super.close();
        return accountList;
    }
}
