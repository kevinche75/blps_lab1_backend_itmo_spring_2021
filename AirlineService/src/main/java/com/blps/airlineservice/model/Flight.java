package com.blps.airlineservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class Flight {

    private long id;

    private String airline;

    private String placeFrom;

    private String placeTo;

    private Double cost;

    private Date DepartureTime;

    private Date ArrivalTime;
}
