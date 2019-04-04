package br.com.bbtcc.controller;

import br.com.bbtcc.model.beans.DocumentTCC;
import br.com.bbtcc.model.dao.TCCDAO;
import br.com.bbtcc.model.dao.factory.DAOAbstractFactory;
import br.com.bbtcc.model.dao.factory.FactoryProducer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

@ManagedBean(name="searchMB")
@SessionScoped
public class SearchManagedBean {
    private DocumentTCC documentTCC;
    private TCCDAO tccdao = null;
    private String searchParameter;
    private List<DocumentTCC> documentTCCList;

    @PostConstruct
    public void init() {
        DAOAbstractFactory daoAbstractFactory = FactoryProducer.getFactory(FactoryProducer.FactoryType.JDBC);
        tccdao = (TCCDAO) daoAbstractFactory.createTCCDAO();
    }

    public void searchDocuments() throws SQLException {
        documentTCCList = tccdao.searchByName(searchParameter);
    }

    public  String documentId(int id) throws SQLException {
        documentTCC = tccdao.searchById(id);
        return "viewTCC";
    }
    
    public void viewDocumentPdf(int docId) throws SQLException {
        DocumentTCC attachment = tccdao.getAttachment(docId);

        FacesContext fc = FacesContext.getCurrentInstance();

        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        response.reset();
        response.setContentType("application/pdf");

        response.setContentLength(attachment.getDocument().length);

        response.setHeader("Content-disposition", "inline; filename="+attachment.getFileName()+"");
        try {
            response.getOutputStream().write(attachment.getDocument());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            fc.responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public DocumentTCC getDocumentTCC() {
        return documentTCC;
    }

    public void setDocumentTCC(DocumentTCC documentTCC) {
        this.documentTCC = documentTCC;
    }

    public TCCDAO getTccdao() {
        return tccdao;
    }

    public void setTccdao(TCCDAO tccdao) {
        this.tccdao = tccdao;
    }

    public String getSearchParameter() {
        return searchParameter;
    }

    public void setSearchParameter(String searchParameter) {
        this.searchParameter = searchParameter;
    }

    public List<DocumentTCC> getDocumentTCCList() {
        return documentTCCList;
    }

    public void setDocumentTCCList(List<DocumentTCC> documentTCCList) {
        this.documentTCCList = documentTCCList;
    }
}
