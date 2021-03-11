package com.blps.app.controller;

import com.blps.app.model.Book;
import com.blps.app.model.Ticket;
import com.blps.app.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public String testRequest(@RequestParam(name = "message") String message){
        return "OK, you entered "+message;
    }

    @GetMapping("/available_tickets")
    public List<Ticket> getAvailableTickets(@RequestParam(name = "from") String from,
                                            @RequestParam(name = "to") String to,
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                            @RequestParam(name = "date") Date date){
        return new ArrayList<Ticket>();
    }

    @GetMapping("approve_book/{id}")
    public HttpStatus approveBook(@PathVariable(name = "id") Long bookId){
        return HttpStatus.OK;
    }

    @GetMapping("book/{by}/{id}")
    public List<Book> getBooks(@PathVariable(name = "by") String by,
                               @PathVariable(name = "id") Long id){
        switch (by){
            case "boss":
                return new ArrayList<Book>();
            case "user":
                return new ArrayList<Book>();
        }
        return null;
    }
    
    @PostMapping("book")
    public HttpStatus createBook(@RequestParam(name = "ticket") Ticket ticket){
        return HttpStatus.OK;
    }
}
