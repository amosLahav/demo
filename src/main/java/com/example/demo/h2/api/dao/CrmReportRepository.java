package com.example.demo.h2.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.h2.api.model.CrmReport;

public interface CrmReportRepository extends JpaRepository<CrmReport, Integer> {

    List<CrmReport> findByErrorCode(int error);
    List<CrmReport> findByStatus(String status);

}
