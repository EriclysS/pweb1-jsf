package br.com.bbtcc.model.beans;

import java.io.Serializable;
import java.util.Objects;

public class DocumentTCC implements Serializable {

    private String title;
    private String course;
    private String description;
    private String fileName;
    private byte[] document;
    private User user;
    private int id;

    public DocumentTCC() {
    }

    public DocumentTCC(String title, String course, String description, User user, int id) {
        this.title = title;
        this.course = course;
        this.description = description;
        this.user = user;
        this.id = id;
    }

    public DocumentTCC(String fileName, byte[] document, int id) {
        this.fileName = fileName;
        this.document = document;
        this.id = id;
    }

    public DocumentTCC(String title, String course, String description, String fileName, byte[] document, User user, int id) {
        this.title = title;
        this.course = course;
        this.description = description;
        this.fileName = fileName;
        this.document = document;
        this.user = user;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentTCC that = (DocumentTCC) o;
        return id == that.id &&
                title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, id);
    }
}
