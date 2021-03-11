package com.blps.app.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "BLPS_FLIGHT")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String airline;

    private String placeFrom;

    private String placeTo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date DepartureTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ArrivalTime;
}
