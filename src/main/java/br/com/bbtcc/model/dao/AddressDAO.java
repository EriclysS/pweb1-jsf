package br.com.bbtcc.model.dao;

import br.com.bbtcc.model.beans.Address;
import br.com.bbtcc.model.beans.User;
import br.com.bbtcc.model.jdbc.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AddressDAO implements IAddressDAO {

    private Connection connection = null;

    public AddressDAO() {
        connection = ConnectionFactory.getInstance().getConnection();
    }

    @Override
    public boolean add(Address address) throws SQLException {

        boolean addressExists = this.searchByAddressUser(address.getUser()).isPresent() ? true : false;

        String query = "";
        if (!addressExists) {
            query = "INSERT INTO endereco( email_usuario, cidade, estado, rua, numero, cep) VALUES (?,?,?,?,?,?);";
        } else {
            return false;
        }
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, address.getUser().getEmail());
        statement.setString(2, address.getCity());
        statement.setString(3, address.getState());
        statement.setString(4, address.getStreet());
        statement.setInt(5, address.getNumber());
        statement.setString(6, address.getZipcode());

        statement.executeUpdate();
        return true;
    }

    @Override
    public Optional<Address> searchByAddressUser(User user) throws SQLException {
        Optional<Address> address = Optional.empty();

        String query = "SELECT * FROM endereco WHERE email_usuario = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getEmail());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            address = Optional.of(new Address(resultSet.getString("rua"),resultSet.getString("cidade"), resultSet.getString("estado"),
                    resultSet.getInt("numero"), resultSet.getString("cep"), user));
        }

        return address;
    }


    @Override
    public boolean update(Address address) throws SQLException {
        boolean userExists = this.searchByAddressUser(address.getUser()).isPresent() ? true : false;

        String query = "";
        if (userExists) {
            query = "UPDATE endereco SET cidade = ?, estado = ?, rua = ?, numero = ?, cep = ? WHERE email_usuario = ?";
        } else {
            return false;
        }
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, address.getCity());
        statement.setString(2, address.getState());
        statement.setString(3, address.getStreet());
        statement.setInt(4, address.getNumber());
        statement.setString(5, address.getZipcode());
        statement.setString(6, address.getUser().getEmail());

        int rs = statement.executeUpdate();
        if(rs == 0){
            return false;
        }
        return true;
    }

}
