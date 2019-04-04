package br.com.bbtcc.controller;

import br.com.bbtcc.model.beans.Address;
import br.com.bbtcc.model.beans.User;
import br.com.bbtcc.model.dao.AddressDAO;
import br.com.bbtcc.model.dao.UserDAO;
import br.com.bbtcc.model.dao.factory.DAOAbstractFactory;
import br.com.bbtcc.model.dao.factory.FactoryProducer;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@ManagedBean(name="usuarioMB")
@RequestScoped
public class UsuarioManagedBean {

    private UserDAO userDAO = null;
    private AddressDAO addressDAO = null;
    private User usuario;
    private Address endereco;


    @PostConstruct
    public void init() {
        DAOAbstractFactory daoAbstractFactory = FactoryProducer.getFactory(FactoryProducer.FactoryType.JDBC);
        userDAO = (UserDAO) daoAbstractFactory.createUserDAO();
        addressDAO = (AddressDAO) daoAbstractFactory.createAddtressDAO();
        usuario = new User();
        endereco = new Address();
    }

    public String cadastrarUsuario() throws SQLException {
        if(userDAO.add(usuario)){
            endereco.setUser(usuario);
            addressDAO.add(endereco);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Usuário cadastrado com sucesso!"));
        } else {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!", "Erro no cadastro de usuário!"));
    }
        return "";
    }



    public String cancelar(){
        return"home";
    }



    //Generated by Map
    private static Map<String,String> estados;
    static{
        estados = new LinkedHashMap<String,String>();
        estados.put("Acre","AC");
        estados.put("Alagoas","AL");
        estados.put("Amapá","AP");
        estados.put("Amazonas","AM");
        estados.put("Bahia","BA");
        estados.put("Ceará","CE");
        estados.put("Distrito Federal","DF");
        estados.put("Espírito Santo","ES");
        estados.put("Goiás","GO");
        estados.put("Maranhão","MA");
        estados.put("Mato Grosso","MT");
        estados.put("Mato Grosso do Sul","MS");
        estados.put("Minas Gerais","MG");
        estados.put("Pará","PA");
        estados.put("Paraíba","PB");
        estados.put("Paraná","PR");
        estados.put("Pernambuco","PE");
        estados.put("Piauí","PI");
        estados.put("Rio de Janeiro","RJ");
        estados.put("Rio Grande do Norte","RN");
        estados.put("Rio Grande do Sul","RS");
        estados.put("Rondônia","RO");
        estados.put("Roraima","RR");
        estados.put("Santa Catarina","RC");
        estados.put("São Paulo","SP");
        estados.put("Sergipe","SE");
        estados.put("Tocantins","TO");
    }


    public Map<String,String> getEstados() {
        return estados;
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
}