package com.example.orvrs;

public class RequestServiceModel {

    String user_email;
    String user_phone;
    String mechanic_email;
    String mechanic_phone;
    String problem;
    String location;
    String status;


    public RequestServiceModel() {
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getMechanic_email() {
        return mechanic_email;
    }

    public void setMechanic_email(String mechanic_email) {
        this.mechanic_email = mechanic_email;
    }

    public String getMechanic_phone() {
        return mechanic_phone;
    }

    public void setMechanic_phone(String mechanic_phone) {
        this.mechanic_phone = mechanic_phone;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RequestServiceModel(String user_email, String user_phone, String mechanic_email, String mechanic_phone, String problem, String location, String status) {
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.mechanic_email = mechanic_email;
        this.mechanic_phone = mechanic_phone;
        this.problem = problem;
        this.location = location;
        this.status = status;
    }
}
