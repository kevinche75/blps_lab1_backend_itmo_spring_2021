package com.blps.app.model;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "BLPS_USER")
public class User implements Serializable{

    @Id
    private String login;

    private String password;

    private String name;

    private String surname;

    private String passport;
    
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @ManyToOne
    private User boss;

    @ManyToOne
    private Company company;
}
