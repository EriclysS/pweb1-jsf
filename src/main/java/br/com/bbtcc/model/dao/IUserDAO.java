package br.com.bbtcc.model.dao;

import br.com.bbtcc.model.beans.User;

import java.sql.SQLException;
import java.util.Optional;

public interface IUserDAO {
    public boolean add(User user) throws SQLException;
    public Optional<User> searchByEmail(String email) throws SQLException;
    public boolean update(User user) throws SQLException;
    public void deleteByEmail(String email) throws SQLException;
    public boolean validateUser(String useremail, String password) throws SQLException;

}
