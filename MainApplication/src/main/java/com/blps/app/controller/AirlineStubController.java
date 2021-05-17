package com.blps.app.controller;

import com.blps.app.model.Book;
import com.blps.app.model.Flight;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/airline/api")
@RestController
public class AirlineStubController {

    @GetMapping("/flights")
    public List<Flight> getFlights(@RequestParam(name = "from") String from,
                                   @RequestParam(name = "to") String to,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name = "date") Date date){

        List<String> airlinesNames = new ArrayList<>();
        airlinesNames.add("Aeroflot");
        airlinesNames.add("S7");
        airlinesNames.add("Pobeda");
        airlinesNames.add("UTair");
        airlinesNames.add("Rossia");

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

    @GetMapping("/flight/{id}")
    public Flight getFlight(@PathVariable(name = "id") Long id){
        Flight flight = new Flight();
        flight.setAirline("Aeroflot");
        flight.setId(id);
        flight.setPlaceFrom("LED");
        flight.setPlaceTo("DME");
        flight.setCost(1000.0);
        flight.setDepartureTime(new Date(System.currentTimeMillis()));
        return flight;
    }

    @PostMapping("/flight/{id}")
    public Long bookTicket(@PathVariable(name = "id") Long id){
        return Math.round(Math.random() * 100000);
    }


    @PostMapping("/ticket/{id}")
    public Boolean buyTicket(@PathVariable(name = "id") Long id){
        return true;
    }
}
