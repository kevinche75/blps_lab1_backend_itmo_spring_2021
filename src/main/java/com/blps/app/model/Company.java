package com.blps.app.model;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "BLPS_COMPANY")
public class Company implements Serializable{

    @Id
    private String name;

    private Double account;
}
