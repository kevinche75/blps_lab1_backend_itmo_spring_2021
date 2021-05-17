package com.blps.app.controller;

import com.blps.app.model.Book;
import com.blps.app.model.Flight;
import com.blps.app.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "${ApiController.getAvailbleTickets}")
    @GetMapping("/tickets")
    public ResponseEntity<List<Flight>> getAvailableTickets(@ApiParam(name = "from") @RequestParam(name = "from") String from,
                                                            @ApiParam(name = "to")@RequestParam(name = "to") String to,
                                                            @ApiParam(name = "date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                            @RequestParam(name = "date") Date date){
        return ResponseEntity.ok(searchService.findTickets(from, to, date));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @ApiOperation(value = "${ApiController.approveBook}")
    @PostMapping("/book/approve")
    public ResponseEntity<Book> approveBook(@RequestParam(name = "id") Long bookId){
        String managerId = SecurityContextHolder.getContext().getAuthentication().getName();
        Book book = bookService.approveBook(bookId, managerId);
        if(book != null){
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @ApiOperation(value = "${ApiController.getBooks}")
    @GetMapping("/{by}/book")
    public ResponseEntity<List<Book>> getBooks(@PathVariable(name = "by") String by){
        List<Book> books;
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
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


    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "${ApiController.createBook}")
    @PostMapping("/book/add")
    public ResponseEntity<Book> createBook(@RequestParam(name = "flight_id") Long flight_id){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Book book = bookService.createBook(flight_id, login);
        if(book!=null){
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.badRequest().body(null);
    }
}
