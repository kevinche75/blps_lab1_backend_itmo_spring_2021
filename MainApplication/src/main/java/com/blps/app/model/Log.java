package com.blps.app.model;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "BLPS_LOG")
public class Log implements Serializable{

    @Id
    @OneToOne
    private Book book;

    @ManyToOne
    private Company company;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date approveTime;
}