package com.twozo.app.service;

import com.twozo.app.dao.ReportDbManager;
import com.twozo.app.model.Report;
import org.springframework.stereotype.Service;

@Service
public class ReportService implements ReportServiceManager{
    private final ReportDbManager reportDataHandler;

    public ReportService(final ReportDbManager reportDataHandler){
        this.reportDataHandler = reportDataHandler;
    }

    public Report get(){
        return reportDataHandler.get();
    }

}
