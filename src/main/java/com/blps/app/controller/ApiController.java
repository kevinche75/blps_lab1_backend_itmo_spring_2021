package com.blps.app.controller;

import com.blps.app.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private final SearchService searchService;
    private final BookService bookService;

    public ApiController(SearchService searchService, BookService bookService){
        this.searchService = searchService;
        this.bookService = bookService;
    }


    @GetMapping("/")
    public String testRequest(@RequestParam(name = "message") String message){
        return "OK, you entered "+message;
    }
}
