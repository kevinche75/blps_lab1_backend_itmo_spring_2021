package com.blps.app.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReportService {

    private KafkaTemplate<String, String> template;

    public ReportService(KafkaTemplate<String, String> template){
        this.template = template;
    }

    public boolean createTask(String userId, Date startDate, Date endDate, String from, String to){
        template.send("aviasales.tasks.add", Long.toString(System.currentTimeMillis()), "Task request");
        return true;
    }
}
