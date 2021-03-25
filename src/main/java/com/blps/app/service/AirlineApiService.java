package com.blps.app.service;

import com.blps.app.model.Flight;
import com.blps.app.model.Ticket;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public Long bookTicket(Flight flight){
        //TODO
        return Math.round(Math.random() * 100000);
    }

    public boolean buyTicket(Ticket ticket){
        //TODO
        return true;
    }

    public Flight checkFlight(Long id){
        Flight flight = new Flight();
        flight.setAirline("Aeroflot");
        flight.setId(id);
        flight.setPlaceFrom("LED");
        flight.setPlaceTo("DME");
        flight.setCost(1000.0);
        flight.setDepartureTime(new Date(System.currentTimeMillis()));
        return flight;
    }

    public List<Flight> findTickets(String from, String to, Date date){
        List<Flight> result = new ArrayList<>();
        long numFlights = 20;
        for(int i=0; i<numFlights; i++){
            Flight flight = new Flight();
            flight.setAirline(airlinesNames.get(i % airlinesNames.size()));
            flight.setPlaceFrom(from);
            flight.setPlaceTo(to);
            flight.setDepartureTime(date);
            flight.setId(i);
            flight.setCost(1000.0);
            result.add(flight);
        }
        return result;
    }

}
