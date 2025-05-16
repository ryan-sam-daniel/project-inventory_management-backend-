package com.twozo.app.dao;

import com.twozo.app.model.Report;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDbManager{
    Report get();
}
