package com.blps.app.service;

import com.blps.app.repository.LogRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private LogRepository logRepository;

    public ReportService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void createStats(){

    }
}
