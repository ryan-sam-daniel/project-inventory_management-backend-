package com.twozo.inventorymanagementsystem.model;

import org.springframework.stereotype.Component;

@Component
public class Customer {
    private int id;
    private String name;
    private String phoneNo;
    private String city;
    private int pincode;

    public Customer(){}

    public Customer(final String name, final String phoneNo, final String city, final int pincode){
        this.name = name;
        this.phoneNo = phoneNo;
        this.city = city;
        this.pincode = pincode;
    }

    public Customer(final int id,final String name, final String phoneNo, final String city, final int pincode){
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.city = city;
        this.pincode = pincode;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(final String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(final int pincode) {
        this.pincode = pincode;
    }
}
