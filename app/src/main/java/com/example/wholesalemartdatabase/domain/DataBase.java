package com.example.wholesalemartdatabase.domain;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;

import com.example.wholesalemartdatabase.data.Customer;
import com.example.wholesalemartdatabase.data.CustomerStatus;
import com.example.wholesalemartdatabase.utils.CollectionUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class DataBase {
    private static DataBase instance;

    public final static String DATABASE_FILENAME = "database.txt";
    private static String DATABASE_FULL_FILE_PATH;

    private ArrayMap<String, Customer> customerArrayMap = new ArrayMap<>();

    private HashMap<String, ArrayList<Customer>> hashMapCustomerName = new HashMap<>();
    private HashMap<String, ArrayList<Customer>> hashMapCustomerSurname = new HashMap<>();
    private HashMap<BigInteger, ArrayList<Customer>> hashMapCustomerBudget = new HashMap<>();
    private HashMap<CustomerStatus, ArrayList<Customer>> hashMapCustomerStatus = new HashMap<>();

    private DataBase() {
        readDataBase();
    }

    public static synchronized DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public ArrayMap<String, Customer> getCustomerArrayMap() {
        return customerArrayMap;
    }

    public void setCustomerArrayMap(ArrayMap<String, Customer> customerArrayMap) {
        this.customerArrayMap = customerArrayMap;
    }

    private void readDataBase() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(DATABASE_FULL_FILE_PATH)))) {
            String line;
            customerArrayMap = new ArrayMap<>();
            while ((line = reader.readLine()) != null) {
                Customer customer = CustomerUtils.parseCustomerFromString(line);
                addNewCustomer(customer);
            }
        } catch (FileNotFoundException e) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DATABASE_FULL_FILE_PATH)))) {
                bufferedWriter.write("");
            } catch (IOException ex) {
                Log.d("ASMR", ex.toString());
            }
        } catch (IOException e) {
            Log.d("ASMR", e.toString());
            e.printStackTrace();
        }
    }

    public boolean addNewCustomer(Customer customer) {
        if (customerArrayMap.containsKey(customer.getPhone())) {
            return false;
        }
        customerArrayMap.put(customer.getPhone(), customer);
        if (hashMapCustomerName.containsKey(customer.getName())) {
            hashMapCustomerName.get(customer.getName()).add(customer);
        } else {
            hashMapCustomerName.put(customer.getName(), new ArrayList<>());
            hashMapCustomerName.get(customer.getName()).add(customer);
        }

        if (hashMapCustomerSurname.containsKey(customer.getSurname())) {
            hashMapCustomerSurname.get(customer.getSurname()).add(customer);
        } else {
            hashMapCustomerSurname.put(customer.getSurname(), new ArrayList<>());
            hashMapCustomerSurname.get(customer.getSurname()).add(customer);
        }

        if (hashMapCustomerBudget.containsKey(customer.getBudget())) {
            hashMapCustomerBudget.get(customer.getBudget()).add(customer);
        } else {
            hashMapCustomerBudget.put(customer.getBudget(), new ArrayList<>());
            hashMapCustomerBudget.get(customer.getBudget()).add(customer);
        }

        if (hashMapCustomerStatus.containsKey(customer.getCustomerStatus())) {
            hashMapCustomerStatus.get(customer.getCustomerStatus()).add(customer);
        } else {
            hashMapCustomerStatus.put(customer.getCustomerStatus(), new ArrayList<>());
            hashMapCustomerStatus.get(customer.getCustomerStatus()).add(customer);
        }

        return true;
    }

    public ArrayList<Customer> search(Bundle bundle) {

        ArrayList<Customer> results = new ArrayList<>();

        if (!bundle.getString("request_phone", "+").equals("+")) {
            if (customerArrayMap.containsKey(bundle.getString("request_phone", "+"))) {
                results.add(customerArrayMap.get(bundle.getString("request_phone", "+")));
                return results;
            }
            return results;
        }

        String name = bundle.getString("request_name", "");
        if (!name.equals("")) {
            if (hashMapCustomerName.containsKey(name)) {
                results.addAll(hashMapCustomerName.get(name));
            } else {
                return results;
            }
        }

        String surname = bundle.getString("request_surname", "");
        if (!surname.equals("")) {
            if (hashMapCustomerSurname.containsKey(surname)) {
                ArrayList<Customer> customers = hashMapCustomerSurname.get(surname);
                if (results.size() == 0) {
                    results.addAll(customers);
                } else {
                    results = (ArrayList<Customer>) CollectionUtils.union(results, customers);
                }
            } else {
                return new ArrayList<>();
            }

            if (results.size() == 0) {
                return results;
            }

        }

        String budgetString = bundle.getString("request_budget", "");
        if (!budgetString.equals("")) {
            BigInteger bigInteger = new BigInteger(budgetString, 10);
            if (hashMapCustomerBudget.containsKey(bigInteger)) {
                ArrayList<Customer> customers = hashMapCustomerBudget.get(bigInteger);
                if (results.size() == 0) {
                    results.addAll(customers);
                } else {
                    results = (ArrayList<Customer>) CollectionUtils.union(results, customers);
                }
            } else {
                return new ArrayList<>();
            }

            if (results.size() == 0) {
                return results;
            }
        }

        int status = bundle.getInt("request_status", -1);
        if (status != -1) {
            CustomerStatus customerStatus = CustomerStatus.BRONZE;

            switch (status) {
                case 0:
                    customerStatus = CustomerStatus.GOLD;
                    break;
                case 1:
                    customerStatus = CustomerStatus.SILVER;
                    break;
            }

            if (hashMapCustomerStatus.containsKey(customerStatus)) {
                ArrayList<Customer> customers = hashMapCustomerStatus.get(customerStatus);
                if (results.size() == 0) {
                    results.addAll(customers);
                } else {
                    results = (ArrayList<Customer>) CollectionUtils.union(results, customers);
                }
            } else {
                return new ArrayList<>();
            }
        }

        return results;
    }

    public void removeCustomerByPhone(String phoneNumber) {
        if (!customerArrayMap.containsKey(phoneNumber)) {
            return;
        }
        Customer customer = customerArrayMap.get(phoneNumber);

        //Status
        if (hashMapCustomerStatus.get(customer.getCustomerStatus()).size() == 1) {
            hashMapCustomerStatus.remove(customer.getCustomerStatus());
        } else {
            hashMapCustomerStatus.get(customer.getCustomerStatus()).remove(customer);
        }

        //Budget
        if (hashMapCustomerBudget.get(customer.getBudget()).size() == 1) {
            hashMapCustomerBudget.remove(customer.getBudget());
        } else {
            hashMapCustomerBudget.get(customer.getBudget()).remove(customer);
        }

        //name
        if (hashMapCustomerName.get(customer.getName()).size() == 1) {
            hashMapCustomerName.remove(customer.getName());
        } else {
            hashMapCustomerName.get(customer.getName()).remove(customer);
        }

        //surname
        if (hashMapCustomerSurname.get(customer.getSurname()).size() == 1) {
            hashMapCustomerSurname.remove(customer.getSurname());
        } else {
            hashMapCustomerSurname.get(customer.getSurname()).remove(customer);
        }

        customerArrayMap.remove(phoneNumber);
    }

    public void saveData() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DATABASE_FULL_FILE_PATH)))) {
            for (Customer customer : customerArrayMap.values()) {
                bufferedWriter.write(CustomerUtils.parseCustomerToString(customer));
            }
        } catch (IOException ex) {
            Log.d("ASMR", ex.toString());
        }
    }

    public static void setDatabaseFullFilePath(String databaseFullFilePath) {
        DATABASE_FULL_FILE_PATH = databaseFullFilePath;
    }

    public void cleanData() {
        customerArrayMap = new ArrayMap<>();
        hashMapCustomerName = new HashMap<>();
        hashMapCustomerSurname = new HashMap<>();
        hashMapCustomerBudget = new HashMap<>();
        hashMapCustomerStatus = new HashMap<>();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DATABASE_FULL_FILE_PATH)))) {
            bufferedWriter.write("");
        } catch (IOException ex) {
            Log.d("ASMR", ex.toString());
        }

    }
}


