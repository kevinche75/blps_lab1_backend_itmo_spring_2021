package com.blps.app.service;

import com.blps.app.model.*;
import com.blps.app.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    private final AirlineApiService airlineApiService;
    private final SearchService searchService;

    public BookService(BookRepository bookRepository,
                       TicketRepository ticketRepository,
                       UserRepository userRepository,
                       AirlineApiService airlineApiService,
                       SearchService searchService){
        this.bookRepository = bookRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.airlineApiService = airlineApiService;
        this.searchService = searchService;
    }

    public boolean createBook(Flight flight, User passenger){

        return true;
    }

    public boolean approveBook(Book book){
        //TODO
        return true;
    }

    public List<Book> getBooks(User user){
        //TODO
        return new ArrayList<>();
    }
}
