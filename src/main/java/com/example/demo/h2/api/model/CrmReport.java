package com.example.demo.h2.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrmReport {
    @Id
    @GeneratedValue
    private int id;
    private int caseId;
    private int customerID;
    private int errorCode;
    private int provider;
    private String productName;
    private String status;
    private Date createDate;
    private Date modifyDate;

    public CrmReport(int i, int i1, int i2, int i3, String s, String s1, Date date, Date date1) {
        modifyDate = date1;
        createDate = date;
        status = s1;
        productName = s;
        provider = i3;
        errorCode = i2;
        customerID = i1;
        caseId=i;
    }
}

