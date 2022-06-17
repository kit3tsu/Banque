package be.bruFormation.banque.Repository;

import be.bruFormation.banque.models.Holder;
import be.bruFormation.banque.utils.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HolderRepository extends Repository{

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
        PreparedStatement statement = super.preparedStatement("SELECT * FROM Holder WHERE id = ? ");
        statement.setInt(1,id);
        ResultSet resultSet = super.executeQuery(statement);
        holder = this.fromResultSet(resultSet);
        super.close();
        return holder;
    }
}
