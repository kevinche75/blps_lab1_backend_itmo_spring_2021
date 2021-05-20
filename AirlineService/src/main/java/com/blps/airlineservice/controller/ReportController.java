package com.blps.airlineservice.controller;


import com.blps.airlineservice.service.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/report/api")
@RestController
public class ReportController {

    private final TaskService taskService;

    public ReportController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task")
    public void createTask(@RequestParam(name = "login") String login,
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                           @RequestParam(name = "startStamp", required = false) Date startStamp,
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                           @RequestParam(name = "endStamp", required = false) Date endStamp,
                           @RequestParam(name = "placeFrom", required = false) String placeFrom,
                           @RequestParam(name = "placeTo", required = false) String placeTo
                           ){
        taskService.createTask(login, startStamp, endStamp, placeFrom, placeTo);
    }
}
