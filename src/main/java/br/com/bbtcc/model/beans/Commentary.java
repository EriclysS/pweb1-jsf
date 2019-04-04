package br.com.bbtcc.model.beans;

import java.io.Serializable;

public class Commentary implements Serializable {

    private DocumentTCC documentTCC;
    private User user;
    private String commentary;
    private int id;

    public Commentary() {
    }

    public Commentary(DocumentTCC documentTCC, User user, String commentary) {
        this.documentTCC = documentTCC;
        this.user = user;
        this.commentary = commentary;
    }

    public Commentary(DocumentTCC documentTCC, User user, String commentary, int id) {
        this.documentTCC = documentTCC;
        this.user = user;
        this.commentary = commentary;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DocumentTCC getDocumentTCC() {
        return documentTCC;
    }

    public void setDocumentTCC(DocumentTCC documentTCC) {
        this.documentTCC = documentTCC;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }
}
