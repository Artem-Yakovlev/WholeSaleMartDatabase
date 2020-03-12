package com.example.wholesalemartdatabase.domain;

import com.example.wholesalemartdatabase.data.Customer;
import com.example.wholesalemartdatabase.data.CustomerStatus;

import java.math.BigInteger;

public class CustomerUtils {

    public static Customer parseCustomerFromString(String line) {
        return new Customer("Artem", "Yakovlev", "+79056644712", new BigInteger("0", 10), CustomerStatus.GOLD);
    }
}
