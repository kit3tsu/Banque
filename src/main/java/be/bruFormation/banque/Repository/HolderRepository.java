package be.bruFormation.banque.Repository;

import be.bruFormation.banque.models.Holder;
import be.bruFormation.banque.utils.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HolderRepository extends Repository {

    @Override
    protected Holder fromResultSet(ResultSet rs) throws SQLException {
        String firstName = rs.getString("last_name");
        String lastName = rs.getString("first_name");
        Holder holder = new Holder(firstName, lastName);
        return holder;
    }

    public List<Holder> findAllHolder() throws SQLException {
        super.open();
        List<Holder> holderList = new ArrayList<>();
        PreparedStatement statement = this.preparedStatement("SELECT * FROM Holder");
        ResultSet resultSet = super.executeQuery(statement);
        while (resultSet.next()) {
            Holder holder = this.fromResultSet(resultSet);
            holderList.add(holder);
        }
        super.close();
        return holderList;
    }

    public Holder findHolderById(int id) throws SQLException {
        super.open();
        Holder holder;
        PreparedStatement statement = super.preparedStatement("SELECT * FROM Holder WHERE holder_id = ? ");
        statement.setInt(1, id);
        ResultSet resultSet = super.executeQuery(statement);
        holder = this.fromResultSet(resultSet);
        super.close();
        return holder;
    }

    public int findHolderIdByName(String name) throws SQLException {
        super.open();
        PreparedStatement statement = super.preparedStatement("SELECT holder_id FROM Holder WHERE last_name = ? ");
        statement.setString(1, name);
        ResultSet resultSet = super.executeQuery(statement);
        int id = resultSet.getInt("holder_id");
        super.close();
        return id;
    }
    public int findHolderIdByNationalNumber(String natNumber) throws SQLException {
        super.open();
        PreparedStatement statement = super.preparedStatement("SELECT holder_id FROM Holder WHERE ssin = ? ");
        statement.setString(1, natNumber);
        ResultSet resultSet = super.executeQuery(statement);
        int id = resultSet.getInt("holder_id");
        super.close();
        return id;
    }
    public void addHolder(Holder holder) throws SQLException {
        super.open();
        String query = "INSERT INTO Account value((SELECT Max(holder_id) FROM Holder)+1,?,?,?)";
        PreparedStatement statement = super.preparedStatement(query);
        statement.setString(1,holder.getNatNumber());
        statement.setString(2,holder.getLastName());
        statement.setString(3,holder.getFirstName());
        ResultSet resultSet = super.executeQuery(statement);
        super.close();
    }
}
