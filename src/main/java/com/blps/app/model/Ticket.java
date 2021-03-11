package com.blps.app.model;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "BLPS_TICKET")
public class Ticket implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "airline")
    private String airline;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "departure_time")
    private Date DepartureTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "arrival_time")
    private Date ArrivalTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
