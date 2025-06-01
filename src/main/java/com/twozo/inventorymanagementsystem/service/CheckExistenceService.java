package com.twozo.inventorymanagementsystem.service;

import org.springframework.stereotype.Service;

@Service
public interface CheckExistenceService {
    boolean checkVendor(String phoneNo);
    boolean checkCustomer(String phoneNo);
    boolean checkPayment(int id);
}
