package com.blps.app.service;

import com.blps.app.model.Flight;
import com.blps.app.model.Ticket;
import com.blps.app.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SearchService {

    private final TicketRepository ticketRepository;
    private final AirlineApiService airlineApiService;

    private SearchService(TicketRepository ticketRepository, AirlineApiService airlineApiService){
        this.ticketRepository = ticketRepository;
        this.airlineApiService = airlineApiService;
    }

    public List<Flight> getTickets(String from, String to, Date date){
        return airlineApiService.findTickets(from, to, date);
    }
}
