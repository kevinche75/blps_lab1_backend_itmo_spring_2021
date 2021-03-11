package com.blps.app.controller;

import com.blps.app.model.Book;
import com.blps.app.model.Flight;
import com.blps.app.model.Ticket;
import com.blps.app.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/api")
@RestController
public class ApiController {

    private final SearchService searchService;
    private final BookService bookService;

    public ApiController(SearchService searchService, BookService bookService){
        this.searchService = searchService;
        this.bookService = bookService;
    }

    @GetMapping("/")
    public ResponseEntity<String> testRequest(@RequestParam(name = "message") String message){
        return ResponseEntity.ok("OK, you entered "+  message);
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<Flight>> getAvailableTickets(@RequestParam(name = "from") String from,
                                            @RequestParam(name = "to") String to,
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                            @RequestParam(name = "date") Date date){
        return ResponseEntity.ok(searchService.getTickets(from, to, date));
    }

    @PostMapping("approve/book")
    public ResponseEntity<Book> approveBook(@RequestParam(name = "id") Long bookId){
        Book book = bookService.approveBook(bookId);
        if(book != null){
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("book/{by}/{id}")
    public ResponseEntity<List<Book>> getBooks(@PathVariable(name = "by") String by,
                               @PathVariable(name = "id") String login){
        List<Book> books;
        switch (by){
            case "boss":
                books =  bookService.getBooksAsBoss(login);
                if(books != null){
                    return ResponseEntity.ok(books);
                }
                else return ResponseEntity.notFound().build();
            case "user":
                books =  bookService.getBooksAsPassenger(login);
                if(books != null){
                    return ResponseEntity.ok(books);
                }
                else return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    
    @PostMapping("add/book")
    public ResponseEntity<Book> createBook(@RequestParam(name = "flight_id") Long flight_id,
                                 @RequestParam(name = "login") String login){
        Book book = bookService.createBook(flight_id, login);
        if(book!=null){
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.badRequest().body(null);
    }
}
