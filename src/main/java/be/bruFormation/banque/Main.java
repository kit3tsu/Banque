package be.bruFormation.banque;

import be.bruFormation.banque.models.Account;
import be.bruFormation.banque.models.CurrentAccount;
import be.bruFormation.banque.models.Holder;
import be.bruFormation.banque.models.SaveAccount;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static final String DB_PATH = Objects.requireNonNull(Main.class.getClassLoader().getResource("banque.sqlite3")).toString();

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        System.out.println(DB_PATH);
        System.out.println("BLOP");
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
        List<Holder> holderList = new ArrayList<>();
        List<Account> accountList = new ArrayList<>();
        getHolder(connection, holderList);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Account");
        ResultSet resultSet = statement.executeQuery();
        Account account;
        while (resultSet.next()) {
            String number = resultSet.getString("number");
            double solde = resultSet.getDouble("solde");
            String desc = resultSet.getString("desc");
            if (desc.equals("CURRENT")) {
                double creditLine = resultSet.getDouble("credit_line");
                account = new CurrentAccount(number, holderList.get(0), solde);
            } else {
                Date date = resultSet.getDate("last_whithdrawan_date");
                LocalDate dateLastWithdrawn = date.toLocalDate();
                account = new SaveAccount(number, holderList.get(0), solde,dateLastWithdrawn);
            }
            accountList.add(account);
        }
        System.out.println(accountList);
    }

    private static void getHolder(Connection connection, List<Holder> holderList) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Holder");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String firstName = resultSet.getString("last_name");
            String lastName = resultSet.getString("first_name");
            Holder holder = new Holder(firstName, lastName);
            holderList.add(holder);
        }
    }
}
