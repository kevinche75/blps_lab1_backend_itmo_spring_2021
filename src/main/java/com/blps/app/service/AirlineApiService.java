package com.blps.app.service;

import com.blps.app.model.Flight;
import com.blps.app.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AirlineApiService {

    //Stub methods emulating airline API (find tickets, book tickets, buy tickets)
    private static List<String> airlinesNames = new ArrayList<>();

    static {
        airlinesNames.add("Aeroflot");
        airlinesNames.add("S7");
        airlinesNames.add("Pobeda");
        airlinesNames.add("UTair");
        airlinesNames.add("Rossia");
    }

    public long bookTicket(Flight flight){
        //TODO
        return Math.round(Math.random() * 100000);
    }

    public boolean buyTicket(Ticket ticket){
        //TODO
        return true;
    }

    public boolean checkTicket(Ticket ticket){
        return true;
    }

    public List<Flight> findTickets(String from, String to, Date date){
        List<Flight> result = new ArrayList<>();
        long numFlights = Math.round(Math.random() * 30);
        for(int i=0; i<numFlights; i++){
            Flight flight = new Flight();
            flight.setAirline(airlinesNames.get(i % airlinesNames.size()));
            flight.setPlaceFrom(from);
            flight.setPlaceTo(to);
            flight.setDepartureTime(date);
            result.add(flight);
        }
        return result;
    }

}
