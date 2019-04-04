package br.com.bbtcc.controller;

import br.com.bbtcc.model.beans.DocumentTCC;
import br.com.bbtcc.model.dao.TCCDAO;
import br.com.bbtcc.model.dao.factory.DAOAbstractFactory;
import br.com.bbtcc.model.dao.factory.FactoryProducer;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@ManagedBean(name="tccMB")
@RequestScoped
public class TCCManagedBean {
    private DocumentTCC documentTCC = null;
    private TCCDAO tccdao = null;
    private Part arquivo;

    @ManagedProperty("#{loginMB}")
    private  LoginManagedBean loginManagedBean;


    public TCCManagedBean() {
    }

    @PostConstruct
    public void init() {
        DAOAbstractFactory daoAbstractFactory = FactoryProducer.getFactory(FactoryProducer.FactoryType.JDBC);
        tccdao = (TCCDAO) daoAbstractFactory.createTCCDAO();
        documentTCC = new DocumentTCC();
    }

    public String cadastroTCC(){
        boolean result = false;
        try(InputStream is = arquivo.getInputStream()) {
            documentTCC .setDocument(is.readAllBytes());
            documentTCC.setFileName(arquivo.getSubmittedFileName());
            documentTCC.setUser(loginManagedBean.getUsuarioLogado());
            result = tccdao.add(documentTCC);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(result){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "TCC cadastrado com sucesso!"));
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!", "Erro no cadastro de usuário!"));
        }
        return "";
    }

    public String cancelar(){
        return "home";
    }



    public DocumentTCC getDocumentTCC() {
        return documentTCC;
    }

    public void setDocumentTCC(DocumentTCC documentTCC) {
        this.documentTCC = documentTCC;
    }

    public Part getArquivo() {
        return arquivo;
    }

    public void setArquivo(Part arquivo) {
        this.arquivo = arquivo;
    }

    public LoginManagedBean getLoginManagedBean() {
        return loginManagedBean;
    }

    public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
        this.loginManagedBean = loginManagedBean;
    }
}
