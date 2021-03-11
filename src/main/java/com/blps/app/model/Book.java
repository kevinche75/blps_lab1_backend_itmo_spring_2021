package com.blps.app.model;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "BOOK")
public class Book implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "book_time")
    private Date BookTime;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private BookStatus status;
}
