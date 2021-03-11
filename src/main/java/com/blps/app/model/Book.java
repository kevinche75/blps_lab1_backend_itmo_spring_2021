package com.blps.app.model;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "BLPS_BOOK")
public class Book implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date BookTime;

    @Enumerated(EnumType.ORDINAL)
    private BookStatus status;
}
