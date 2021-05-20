package com.blps.airlineservice.service;

import com.blps.airlineservice.repository.LogRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private LogRepository logRepository;

    public ReportService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void createStats(){
        System.out.println("Quarts is working");
    }
}
