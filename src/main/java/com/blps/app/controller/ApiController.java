package com.blps.app.controller;

import com.blps.app.model.Flight;
import com.blps.app.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class ApiController {

    private final SearchService searchService;
    private final BookService bookService;

    public ApiController(SearchService searchService, BookService bookService){
        this.searchService = searchService;
        this.bookService = bookService;
    }


    @GetMapping("/")
    public List<Flight> testRequest(@RequestParam(name = "message") String message){
        return searchService.getTickets("LED", "DME", new Date(System.currentTimeMillis()));
    }
}
