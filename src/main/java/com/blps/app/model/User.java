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
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "passport")
    private String passport;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "boss_id")
    private User boss;

    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;
}
