package com.blps.airlineservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "BLPS_TASK")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private User creator;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startStamp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endStamp;

    private String placeFrom;

    private String placeTo;
}
