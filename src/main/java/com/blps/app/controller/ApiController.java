package com.blps.app.controller;

import com.blps.app.model.Book;
import com.blps.app.model.Flight;
import com.blps.app.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/api/")
@RestController
public class ApiController {

    private final AirlineApiService searchService;
    private final BookService bookService;

    public ApiController(AirlineApiService searchService, BookService bookService){
        this.searchService = searchService;
        this.bookService = bookService;
    }

    @ApiOperation(value = "${ApiController.getAvailbleTickets}")
    @GetMapping("/tickets")
    public ResponseEntity<List<Flight>> getAvailableTickets(@ApiParam(name = "from") @RequestParam(name = "from") String from,
                                                            @ApiParam(name = "to")@RequestParam(name = "to") String to,
                                                            @ApiParam(name = "date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                            @RequestParam(name = "date") Date date){
        return ResponseEntity.ok(searchService.findTickets(from, to, date));
    }

    @ApiOperation(value = "${ApiController.approveBook}")
    @PostMapping("/book/approve")
    public ResponseEntity<Book> approveBook(@RequestParam(name = "id") Long bookId){
        Book book = bookService.approveBook(bookId);
        if(book != null){
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ApiOperation(value = "${ApiController.getBooks}")
    @GetMapping("/{by}/{login}/book")
    public ResponseEntity<List<Book>> getBooks(@PathVariable(name = "by") String by,
                               @PathVariable(name = "login") String login){
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

    @ApiOperation(value = "${ApiController.createBook}")
    @PostMapping("/book/add")
    public ResponseEntity<Book> createBook(@RequestParam(name = "flight_id") Long flight_id,
                                 @RequestParam(name = "login") String login){
        Book book = bookService.createBook(flight_id, login);
        if(book!=null){
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.badRequest().body(null);
    }
}
