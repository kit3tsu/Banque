package be.bruFormation.banque.Repository;

import be.bruFormation.banque.models.Account;
import be.bruFormation.banque.models.Bank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankRepository extends Repository {
    @Override
    protected Bank fromResultSet(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        int bank_id = rs.getInt("bank_id");
        //return new Bank(name,this.findAllAccountOfTheBankById(bank_id));
        // TODO Retrive all existing account by id in a map of String and Account
        return new Bank(name);
    }

    public List<Bank> findAllBank() throws SQLException {
        super.open();
        List<Bank> BankList = new ArrayList<>();
        PreparedStatement statement = super.preparedStatement("SELECT * FROM Bank");
        ResultSet resultSet = super.executeQuery(statement);
        while (resultSet.next()) {
            Bank Bank = this.fromResultSet(resultSet);
            BankList.add(Bank);
        }
        super.close();
        return BankList;
    }

    public Bank findBankById(int id) throws SQLException {
        super.open();
        Bank Bank;
        PreparedStatement statement = super.preparedStatement("SELECT * FROM Bank WHERE id = ? ");
        statement.setInt(1, id);
        ResultSet resultSet = super.executeQuery(statement);
        Bank = this.fromResultSet(resultSet);
        super.close();
        return Bank;
    }

    private List<Account> findAllAccountOfTheBankById(int bank_id) throws SQLException {
        AccountRepository accountRepository = new AccountRepository();
        super.open();
        List<Account> accountList = new ArrayList<>();
        PreparedStatement statement = super.preparedStatement("SELECT * FROM Account WHERE bank_id = ?");
        statement.setInt(1,bank_id);
        ResultSet resultSet = super.executeQuery(statement);
        while (resultSet.next()) {
            accountList.add(accountRepository.fromResultSet(resultSet));
        }
        super.close();
        return accountList;
    }


}
