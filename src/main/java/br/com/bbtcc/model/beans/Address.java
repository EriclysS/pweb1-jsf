package br.com.bbtcc.model.beans;

import java.io.Serializable;

public class Address implements Serializable {
    private String street;
    private String city;
    private String state;
    private int number;
    private String zipcode;
    private User user;

    public Address() {
    }

    public Address(String street, String city, String state, int number, String zipcode, User user) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.number = number;
        this.zipcode = zipcode;
        this.user = user;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}