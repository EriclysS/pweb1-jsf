package br.com.bbtcc.model.dao;

import br.com.bbtcc.model.beans.Address;
import br.com.bbtcc.model.beans.User;

import java.sql.SQLException;
import java.util.Optional;

public interface IAddressDAO {
    public boolean add(Address address) throws SQLException;
    public Optional<Address> searchByAddressUser(User user) throws SQLException;
    public boolean update(Address address) throws SQLException;
}
