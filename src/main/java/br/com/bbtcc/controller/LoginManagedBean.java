package br.com.bbtcc.controller;

import br.com.bbtcc.model.beans.User;
import br.com.bbtcc.model.dao.UserDAO;
import br.com.bbtcc.model.dao.factory.DAOAbstractFactory;
import br.com.bbtcc.model.dao.factory.FactoryProducer;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Optional;

@ManagedBean(name="loginMB")
@SessionScoped
public class LoginManagedBean {

    private UserDAO userDAO = null;
    private String email;
    private String senha;
    private User usuarioLogado;



    @PostConstruct
    public void init() {
        DAOAbstractFactory daoAbstractFactory = FactoryProducer.getFactory(FactoryProducer.FactoryType.JDBC);
        userDAO = (UserDAO) daoAbstractFactory.createUserDAO();
    }

    public String autenticacao() throws Exception {
        Optional<User> usuario = userDAO.searchByEmail(email);

        if (!usuario.isPresent() || !usuario.get().getPassword().equals(senha)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciais inválidas, tente novamente.", "Credenciais inválidas, tente novamente."));
            return "";
        }

        usuarioLogado = usuario.map( u -> { u.setPassword(null); return u; }).get();
        email = null;
        senha = null;
        userDAO = null;

        return "home";
    }


    public String logout() {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession(false).invalidate();
        return "home";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public User getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(User usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}
