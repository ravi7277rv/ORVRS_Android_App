package com.example.orvrs;

public class MechanicsModel {

    String name;
    String email;
    String phone;
    String charges;
    String exp_year;
    String expertIn;
    String address;
    String password;

    public MechanicsModel() {
    }

    public MechanicsModel(String name, String email, String phone, String charges, String exp_year, String expertIn, String address, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.charges = charges;
        this.exp_year = exp_year;
        this.expertIn = expertIn;
        this.address = address;
        this.password = password;
    }


    public MechanicsModel(String name, String email, String phone, String charges, String exp_year, String expertIn, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.charges = charges;
        this.exp_year = exp_year;
        this.expertIn = expertIn;
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getExp_year() {
        return exp_year;
    }

    public void setExp_year(String exp_year) {
        this.exp_year = exp_year;
    }

    public String getExpertIn() {
        return expertIn;
    }

    public void setExpertIn(String expertIn) {
        this.expertIn = expertIn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
}
