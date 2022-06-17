package be.bruFormation.banque;

import be.bruFormation.banque.Repository.HolderRepository;
import be.bruFormation.banque.models.Account;
import be.bruFormation.banque.models.CurrentAccount;
import be.bruFormation.banque.models.SaveAccount;
import be.bruFormation.banque.utils.Config;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    //public static final String DB_PATH = Objects.requireNonNull(Main.class.getClassLoader().getResource("banque.sqlite3")).toString();

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Config db = new Config();
        HolderRepository holderRepository = new HolderRepository();
        Connection connection = DriverManager.getConnection(Config.Db.getUrl());
        List<Account> accountList = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Account");
        ResultSet resultSet = statement.executeQuery();
        Account account;
        while (resultSet.next()) {
            String number = resultSet.getString("number");
            double solde = resultSet.getDouble("solde");
            String desc = resultSet.getString("desc");
            if (desc.equals("CURRENT")) {
                double creditLine = resultSet.getDouble("credit_line");
                account = new CurrentAccount(number, holderRepository.findHolderById(1), solde);
            } else {
                Date date = resultSet.getDate("last_whithdrawan_date");
                LocalDate dateLastWithdrawn = date.toLocalDate();
                account = new SaveAccount(number, holderRepository.findHolderById(1), solde,dateLastWithdrawn);
            }
            accountList.add(account);
        }
        System.out.println(accountList);
    }

}
