package com.twozo.inventorymanagementsystem.service;

import com.twozo.inventorymanagementsystem.model.Customer;

public interface CustomerService {
    int addCustomer(Customer customer);
    Customer get(String phone);
}
