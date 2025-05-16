package com.twozo.app.service;

import com.twozo.app.model.Report;
import org.springframework.stereotype.Service;

@Service
public interface ReportServiceManager {
    Report get();
}
