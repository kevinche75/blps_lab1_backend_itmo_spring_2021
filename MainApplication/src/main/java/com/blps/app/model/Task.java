package com.blps.app.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "BLPS_TASK")
public class Task {

    @Id
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
