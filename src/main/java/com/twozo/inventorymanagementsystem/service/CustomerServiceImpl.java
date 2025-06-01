package com.twozo.inventorymanagementsystem.service;

import com.twozo.inventorymanagementsystem.dao.CustomerDao;
import com.twozo.inventorymanagementsystem.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{
    private final CustomerDao<Customer> customerDbHandler;

    public CustomerServiceImpl(final CustomerDao<Customer> customerDbHandler){
        this.customerDbHandler = customerDbHandler;
    }

    @Override
    public int addCustomer(final Customer customer){
        return customerDbHandler.store(customer);
    }

    @Override
    public Customer get(final String phoneNo){ return customerDbHandler.get(phoneNo); }
}
