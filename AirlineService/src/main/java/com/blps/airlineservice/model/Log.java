package com.blps.airlineservice.model;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "BLPS_LOG")
public class Log implements Serializable{

    @Id
    private Long bookId;

    @ManyToOne
    private Company company;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date approveTime;
}