package br.com.bbtcc.model.beans;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private boolean type;
    private String name;
    private String email;
    private char sex;
    private String telephone;
    private String password;
    private byte[] image;

    public User() {
    }

    public User(boolean type, String name, String email, char sex, String telephone, String password, byte[] image) {
        this.type = type;
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.telephone = telephone;
        this.password = password;
        this.image = image;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}