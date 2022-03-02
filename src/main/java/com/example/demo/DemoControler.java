package com.example.demo;

import com.example.demo.h2.api.dao.CrmReportRepository;
import com.example.demo.h2.api.model.CrmReport;
import com.example.demo.h2.api.model.ScheduledTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class DemoControler {

    @Autowired
    private CrmReportRepository repository;

    @RequestMapping
    public String helloWord(){
        return "12 33 44 55 66t";
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
    @RequestMapping("/load")
    public String load(@RequestParam(value = "fileName", defaultValue = "banana") String fileName) throws IOException {

 //       String data = new String(Files.readAllBytes(Paths.get("./banana.json")));
        String data = new String(Files.readAllBytes(Paths.get("./" + fileName +".json")));
        try {
        JSONObject object01 = new JSONObject(data);
         //read as json array
        JSONArray jsonArray = object01.getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++) {
                // parse in json object
                JSONObject object1 = jsonArray.getJSONObject(i);

                String createDateString = object1.getString("TICKET_CREATION_DATE");
                String modifyDateString = object1.getString("LAST_MODIFIED_DATE");
                Date createDate = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(createDateString);
                Date modifyDate = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(modifyDateString);

                int caseId = object1.getInt("Case ID");
                int customerID = object1.getInt("Customer ID");
                int errorCode = object1.getInt("CREATED_ERROR_CODE");
                int provider = object1.getInt("Provider");
                String productName = object1.getString("PRODUCT_NAME");
                String status = object1.getString("STATUS");

                CrmReport crmReport = new CrmReport(caseId,
                        customerID,
                        errorCode,
                        provider,productName,
                        status,
                        createDate,
                        modifyDate);

                repository.save(crmReport);

                System.out.println("PRODUCT_NAME: " + productName);
                System.out.println("provider: " + provider);
                System.out.println("caseId: " + caseId);
                System.out.println("errorCode: " + errorCode);
                System.out.println("--- saved---");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "load failed";
        } catch (ParseException e) {
            e.printStackTrace();
            return "load failed 2";
        }
        return "load ok";
    }

    @RequestMapping("/reload")
    public String reload() throws IOException {
        try {
            repository.deleteAll();
            load("banana");
            load("srawberry");
        }catch (IOException e) {
                e.printStackTrace();
                return "reload failed";
        }
        return "reloaded";
    }

    }
