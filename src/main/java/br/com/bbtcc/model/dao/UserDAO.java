package br.com.bbtcc.model.dao;

import br.com.bbtcc.model.beans.User;
import br.com.bbtcc.model.jdbc.ConnectionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

public class UserDAO implements IUserDAO {


    private Connection connection;

    public UserDAO() {
        connection = ConnectionFactory.getInstance().getConnection();
    }

    private static Logger logger = Logger.getLogger(UserDAO.class.getName());


    public boolean add(User user) throws SQLException {
        boolean userExists = this.searchByEmail(user.getEmail()).isPresent() ? true : false;

        String query = "";
        if (!userExists) {
            query = "INSERT INTO usuario( tipo_usuario, nome, sexo, email, senha, telefone) VALUES (?,?,?,?,?,?);";
        } else {
            return false;
        }
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setBoolean(1, user.isType());
        statement.setString(2, user.getName());
        statement.setString(3, String.valueOf(user.getSex()));
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getPassword());
        statement.setString(6, user.getTelephone());

        statement.executeUpdate();
        return true;

    }

    public int uploadImage(User user, InputStream image) throws SQLException, IOException {

        String query = "UPDATE usuario set foto_perfil=? WHERE email=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setBytes(1,  image.readAllBytes());
        statement.setString(2, user.getEmail());
        int result = statement.executeUpdate();
        return result;
    }


    @Override
    public Optional<User> searchByEmail(String email) throws SQLException {
        Optional<User> user = Optional.empty();

        String query = "SELECT * FROM usuario WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = Optional.of(new User(resultSet.getBoolean("tipo_usuario"), resultSet.getString("nome"),
                    resultSet.getString("email"), resultSet.getString("sexo").charAt(0),
                    resultSet.getString("telefone"), resultSet.getString("senha"), resultSet.getBytes("foto_perfil")));
        }

        return user;
    }

    @Override
    public boolean update(User user) throws SQLException {

        String query = "UPDATE usuario SET nome = ?, telefone = ? WHERE email = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getName());
        statement.setString(2, user.getTelephone());
        statement.setString(3, user.getEmail());
        int resultSet = statement.executeUpdate();
        if (resultSet == 0){
            return false;
        }
        return true;
    }
    @Override
    public void deleteByEmail(String email) throws SQLException {
        String query = "DELETE FROM usuario WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        statement.executeUpdate();
    }

    @Override
    public boolean validateUser(String useremail, String password) throws SQLException {
        boolean isValidUser = false;
        //white the select query
        String sql = "select * from usuario where email=? and senha=?";

        //set parameter with prepare statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, useremail);
        statement.setString(2, password);

        //execute the statement and check whether user exists
        ResultSet Set = statement.executeQuery();
        while (Set.next()) {
            isValidUser = true;
        }
        return isValidUser;
    }

}