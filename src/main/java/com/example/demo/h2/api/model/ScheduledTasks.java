
package com.example.demo.h2.api.model;
/*
public class ScheduledTasks {
}
*/
/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//package com.example.schedulingtasks;

        import java.io.IOException;
        import java.nio.file.Files;
        import java.nio.file.Paths;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Date;

        import com.example.demo.h2.api.dao.CrmReportRepository;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.configurationprocessor.json.JSONArray;
        import org.springframework.boot.configurationprocessor.json.JSONException;
        import org.springframework.boot.configurationprocessor.json.JSONObject;
        import org.springframework.scheduling.annotation.Scheduled;
        import org.springframework.stereotype.Component;
        import org.springframework.stereotype.Service;
        import org.springframework.web.bind.annotation.RequestParam;

@Component
public class ScheduledTasks {
    @Autowired
    private CrmReportRepository repository;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

/*
    @Scheduled(fixedRate = 1500)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
*/

    @Scheduled(initialDelay = 40000,fixedRateString = "${fixed-rate.in.milliseconds}")
    public void getSchedule() throws IOException {
        log.info("reloading {}", dateFormat.format(new Date()));
        repository.deleteAll();
        load("banana");
        load("srawberry");
    }

    private String load(String fileName) throws IOException {
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

}