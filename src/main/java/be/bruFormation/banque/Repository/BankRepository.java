package be.bruFormation.banque.Repository;

import be.bruFormation.banque.models.Account;
import be.bruFormation.banque.models.Bank;
import be.bruFormation.banque.models.Holder;

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
    public int findBankIdBySwift(String swift) throws SQLException {
        super.open();
        PreparedStatement statement = super.preparedStatement("SELECT id FROM Bank WHERE swift_number = ? ");
        statement.setString(1, swift);
        ResultSet resultSet = super.executeQuery(statement);// TODO result set for 1 argument
        int id = resultSet.getInt("id");
        super.close();
        return id;
    }
    public void addBank(Bank bank) throws SQLException {
        super.open();
        String query = "INSERT INTO Account value((SELECT Max(bank_id) FROM Bank)+1,?,?)";
        PreparedStatement statement = super.preparedStatement(query);
        statement.setString(1,bank.getName());
        statement.setString(2,bank.getSwiftCode());
        ResultSet resultSet = super.executeQuery(statement);
        super.close();
    }

}
