package com.example.wholesalemartdatabase.data;

import java.math.BigInteger;

public class Customer {

    private String name;
    private String phone;
    private BigInteger budget;
    private CustomerStatus customerStatus;

    public Customer(String name, String phone, BigInteger budget, CustomerStatus customerStatus) {
        this.name = name;
        this.phone = phone;
        this.budget = budget;
        this.customerStatus = customerStatus;
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

    public BigInteger getBudget() {
        return budget;
    }

    public void setBudget(BigInteger budget) {
        this.budget = budget;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }
}
