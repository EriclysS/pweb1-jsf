package br.com.bbtcc.controller;

import br.com.bbtcc.model.beans.Address;
import br.com.bbtcc.model.beans.User;
import br.com.bbtcc.model.dao.AddressDAO;
import br.com.bbtcc.model.dao.UserDAO;
import br.com.bbtcc.model.dao.factory.DAOAbstractFactory;
import br.com.bbtcc.model.dao.factory.FactoryProducer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import java.sql.SQLException;

@ManagedBean(name="perfilMB")
@ViewScoped
public class PerfilManagedBean {
    private UserDAO userDAO = null;
    private AddressDAO addressDAO = null;
    private User usuario;
    private Address endereco;

    @ManagedProperty("#{loginMB}")
    private  LoginManagedBean loginManagedBean;

    @PostConstruct
    public void init() {
        DAOAbstractFactory daoAbstractFactory = FactoryProducer.getFactory(FactoryProducer.FactoryType.JDBC);
        userDAO = (UserDAO) daoAbstractFactory.createUserDAO();
        addressDAO = (AddressDAO) daoAbstractFactory.createAddtressDAO();
        usuario = new User();
        endereco = new Address();
    }

    public String perfil() throws SQLException {
        usuario = userDAO.searchByEmail(loginManagedBean.getUsuarioLogado().getEmail()).get();
        endereco = addressDAO.searchByAddressUser(usuario).get();
        return "perfil";
    }


    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Address getEndereco() {
        return endereco;
    }

    public void setEndereco(Address endereco) {
        this.endereco = endereco;
    }

    public LoginManagedBean getLoginManagedBean() {
        return loginManagedBean;
    }

    public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
        this.loginManagedBean = loginManagedBean;
    }
}
