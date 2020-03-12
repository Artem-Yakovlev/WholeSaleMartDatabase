package com.example.wholesalemartdatabase.domain;

import com.example.wholesalemartdatabase.data.Customer;
import com.example.wholesalemartdatabase.data.CustomerStatus;

import java.math.BigInteger;

public class CustomerUtils {

    public static Customer parseCustomerFromString(String line) {
        String[] parsed = line.split(" ");
        String name = parsed[0];
        String surname = parsed[1];
        String phone = parsed[2];
        BigInteger budget = new BigInteger(parsed[3]);
        CustomerStatus customerStatus = CustomerStatus.BRONZE;

        if (parsed[4].equals("GOLD")) {
            customerStatus = CustomerStatus.GOLD;
        } else if (parsed[4].equals("SILVER")) {
            customerStatus = CustomerStatus.SILVER;
        }

        return new Customer(name, surname, phone, budget, customerStatus);
    }

    public static String parseCustomerToString(Customer customer) {
        return customer.getName() + " " + customer.getSurname() + " " + customer.getPhone() + " " + customer.getBudget() + " " + customer.getCustomerStatus() + "\n";
    }
}
