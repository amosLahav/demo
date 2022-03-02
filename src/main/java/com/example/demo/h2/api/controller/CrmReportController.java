package com.example.demo.h2.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.h2.api.dao.CrmReportRepository;
import com.example.demo.h2.api.model.CrmReport;

@RestController
public class CrmReportController {
    @Autowired
    private CrmReportRepository repository;

    @PostMapping("/saveCrmReport")
    public String saveCrmReport(@RequestBody CrmReport crmReport) {
        repository.save(crmReport);
        return "CrmReport saved..";
    }

    @GetMapping("/getAllCrmReport")
    public List<CrmReport> getAll() {
        return repository.findAll();
    }

    @GetMapping("/getCrmReportByErrorCode/{code}")
    public List<CrmReport> getCrmReportByErrorCode(@PathVariable int code) {
        return repository.findByErrorCode(code);
    }
    @GetMapping("/getCrmReportByStatus/{code}")
    public List<CrmReport> getCrmReportByStatus(@PathVariable String code) {
        return repository.findByStatus(code);
    }

}
