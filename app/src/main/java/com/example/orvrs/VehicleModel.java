package com.example.orvrs;

public class VehicleModel {
    String OwnerName,VehicleNumber,VehicleModel,VehicleChassisNo,email,mobile;

    public VehicleModel() {
    }

    public VehicleModel(String ownerName, String vehicleNumber, String vehicleModel, String vehicleChassisNo, String email, String mobile) {
        OwnerName = ownerName;
        VehicleNumber = vehicleNumber;
        VehicleModel = vehicleModel;
        VehicleChassisNo = vehicleChassisNo;
        this.email = email;
        this.mobile = mobile;
    }

    public VehicleModel(String ownerName, String vehicleNumber, String vehicleModel, String vehicleChassisNo) {
        OwnerName = ownerName;
        VehicleNumber = vehicleNumber;
        VehicleModel = vehicleModel;
        VehicleChassisNo = vehicleChassisNo;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }

    public String getVehicleModel() {
        return VehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        VehicleModel = vehicleModel;
    }

    public String getVehicleChassisNo() {
        return VehicleChassisNo;
    }

    public void setVehicleChassisNo(String vehicleChassisNo) {
        VehicleChassisNo = vehicleChassisNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
