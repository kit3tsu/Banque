package be.bruFormation.banque.Repository;

import be.bruFormation.banque.utils.Config;
import com.google.common.reflect.Reflection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public abstract class Repository {
    private Connection connection;

    protected abstract <T> T fromResultSet(ResultSet rs) throws SQLException;

    protected void open() throws SQLException {
        connection = DriverManager.getConnection(Config.Db.getUrl());
    }

    protected void close() throws SQLException {
        connection.close();
    }

    protected PreparedStatement preparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
    protected Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    protected ResultSet executeQuery(String query) throws SQLException {
        Statement statement = createStatement();
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }
    protected ResultSet executeQuery(PreparedStatement builder) throws SQLException {
        ResultSet rs = builder.executeQuery();
        return rs;
    }
}

