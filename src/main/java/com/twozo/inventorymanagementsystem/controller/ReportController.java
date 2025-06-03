package com.twozo.inventorymanagementsystem.controller;

import com.twozo.inventorymanagementsystem.model.Report;
import com.twozo.inventorymanagementsystem.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class ReportController {

    private final ReportService reportService;

    public ReportController(final ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report")
    public ResponseEntity<Report> getReport() {
        final Report report = reportService.get();
        return report != null ? ResponseEntity.ok(report) : ResponseEntity.notFound().build();
    }

}
