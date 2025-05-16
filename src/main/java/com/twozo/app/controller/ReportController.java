package com.twozo.app.controller;

import com.twozo.app.model.Report;
import com.twozo.app.service.ReportServiceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class ReportController {
    private final ReportServiceManager reportService;

    public ReportController(final ReportServiceManager reportService){
        this.reportService = reportService;
    }

    @GetMapping("/report")
    public ResponseEntity<Report> getReport(){
        final Report report = reportService.get();
        return report != null ? ResponseEntity.ok(report) : ResponseEntity.notFound().build();
    }

}
