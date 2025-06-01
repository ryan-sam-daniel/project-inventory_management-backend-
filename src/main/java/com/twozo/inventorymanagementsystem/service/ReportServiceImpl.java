package com.twozo.inventorymanagementsystem.service;

import com.twozo.inventorymanagementsystem.dao.ReportDao;
import com.twozo.inventorymanagementsystem.model.Report;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportDao reportDataHandler;

    public ReportServiceImpl(final ReportDao reportDataHandler){
        this.reportDataHandler = reportDataHandler;
    }

    public Report get(){
        return reportDataHandler.get();
    }

}
