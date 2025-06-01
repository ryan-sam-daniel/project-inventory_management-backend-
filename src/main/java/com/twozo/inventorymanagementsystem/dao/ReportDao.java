package com.twozo.inventorymanagementsystem.dao;

import com.twozo.inventorymanagementsystem.model.Report;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDao{
    Report get();
}
