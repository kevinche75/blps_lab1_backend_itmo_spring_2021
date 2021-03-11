package com.blps.app.model;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "BLPS_COMPANY")
public class Company implements Serializable{

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "account")
    private Double account;
}
