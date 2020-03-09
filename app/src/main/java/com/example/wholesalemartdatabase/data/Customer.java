package com.example.wholesalemartdatabase.data;

import java.math.BigInteger;

public class Customer {

    private int id;
    private String name;
    private String phone;
    private BigInteger totalPurchases;
    private CustomerStatus customerStatus;

    public Customer(int id, String name, String phone, BigInteger totalPurchases, CustomerStatus customerStatus) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.totalPurchases = totalPurchases;
        this.customerStatus = customerStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigInteger getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(BigInteger totalPurchases) {
        this.totalPurchases = totalPurchases;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }
}
