package com.blps.app.service;

import com.blps.app.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AirlineApiService {

    //Stub methods emulating airline API (find tickets, book tickets, buy tickets)

    public boolean bookTicket(Ticket ticket){
        //TODO
        return true;
    }

    public boolean buyTicket(Ticket ticket){
        //TODO
        return true;
    }

    public List<Ticket> findTickets(String from, String to, Date date){
        //TODO
        return new ArrayList<>();
    }

}
