package com.blps.app.service;

import com.blps.app.model.*;
import com.blps.app.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;

    private final AirlineApiService airlineApiService;

    public BookService(BookRepository bookRepository,
                       TicketRepository ticketRepository,
                       UserRepository userRepository,
                       FlightRepository flightRepository,
                       AirlineApiService airlineApiService){
        this.bookRepository = bookRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.airlineApiService = airlineApiService;
    }

    public Book createBook(Long flightId, String login){
        Optional<User> passenger = userRepository.findById(login);
        Flight flight = airlineApiService.checkFlight(flightId);
        if(passenger.isPresent() && flight != null){
            Long ticketNumber = airlineApiService.bookTicket(flight);
            if(ticketNumber != null){
                Ticket ticket = new Ticket();
                ticket.setPassenger(passenger.get());
                ticket.setFlight(flight);
                ticket.setInnerNumber(ticketNumber);
                Book book = new Book();
                book.setBookTime(new Date(System.currentTimeMillis()));
                book.setBoss(passenger.get().getBoss());
                book.setCreator(passenger.get());
                book.setStatus(BookStatus.BOOKED);
                book.setTickets(new ArrayList<>());
                ticket.setBook(book);
                if(!flightRepository.existsById(flightId)){
                    flightRepository.saveAndFlush(flight);
                }
                bookRepository.saveAndFlush(book);
                ticketRepository.saveAndFlush(ticket);
                book.getTickets().add(ticket);
                return book;
            }
        }
        return null;
    }

    public Book approveBook(Long bookId, String managerId){
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Optional<User> manager = userRepository.findById(managerId);
        if(bookOptional.isPresent() && bookOptional.get().getStatus().equals(BookStatus.BOOKED) && manager.isPresent() && manager.get().equals(bookOptional.get().getBoss())){
            Book book = bookOptional.get();
            double bookSum = 0;
            for(Ticket t : book.getTickets()){
                bookSum+=t.getFlight().getCost();
            }
            if(bookSum > book.getBoss().getCompany().getAccount()){
                return null;
            }
            for(Ticket t : book.getTickets()){
                if(airlineApiService.buyTicket(t)){
                    book.getBoss().getCompany().setAccount(book.getBoss().getCompany().getAccount() - t.getFlight().getCost());
                }
                else return null;
            }
            book.setStatus(BookStatus.APPROVED);
            bookRepository.saveAndFlush(book);
            return book;
        }
        return null;
    }

    public List<Book> getBooksAsPassenger(String login){
        Optional<User> passenger = userRepository.findById(login);
        return passenger.map(User::getBooksCreator).orElse(null);
    }

    public List<Book> getBooksAsBoss(String login){
        Optional<User> passenger = userRepository.findById(login);
        return passenger.map(User::getBooksBoss).orElse(null);
    }
}
