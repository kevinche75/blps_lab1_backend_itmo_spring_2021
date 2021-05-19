package com.blps.app.service;

import com.blps.app.model.Flight;
import com.blps.app.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AirlineApiService {

    @Value("${app.airline.url}")
    private String airlineUrl;

    @Autowired
    private KafkaTemplate<String, String> template;

    public Long bookTicket(Flight flight){
        return new RestTemplate().postForObject(airlineUrl+"flight/{id}", "", Long.class, flight.getId());
    }

    public boolean buyTicket(Ticket ticket){
        Boolean status = new RestTemplate().postForObject(airlineUrl+"ticket/{id}", "", Boolean.class, ticket.getId());
        if(status != null){
            return status;
        }
        return false;
    }

    public Flight checkFlight(Long id){
        return new RestTemplate().getForObject(airlineUrl+"flight/{id}", Flight.class, id);
    }

    public List<Flight> findTickets(String from, String to, Date date){
        template.send("flights", Long.toString(System.currentTimeMillis()), "FLIGHT SEARCH").addCallback(System.out::println, System.err::println);
        template.flush();
        Flight[] flights = new RestTemplate()
                .getForEntity(airlineUrl+"flights?from={from}&to={to}&date={date}", Flight[].class, from, to, new SimpleDateFormat("yyyy-MM-dd").format(date)).getBody();
        if(flights==null){
            return null;
        }
        return new ArrayList<>(Arrays.asList(flights));
    }

}
