package be.bruFormation.banque;

import be.bruFormation.banque.models.Bank;
import be.bruFormation.banque.models.CurrentAccount;
import be.bruFormation.banque.models.Holder;

import java.sql.*;

public class Main {
    private static final String DB_PATH = Main.class.getClassLoader().getResource("dbslide.sqlite3").toString();

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        //System.out.println(path);
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM student");
        //statement.setInt(1,1020);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("first_name"));
        }
    }
}
