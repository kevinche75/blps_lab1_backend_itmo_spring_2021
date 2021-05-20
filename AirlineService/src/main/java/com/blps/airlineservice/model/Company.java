package com.blps.airlineservice.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "BLPS_COMPANY")
@JsonIgnoreProperties({"users"})
public class Company implements Serializable{

    @Id
    private String name;

    private Double account;

    @OneToMany(mappedBy = "company")
    private List<User> users;
}
