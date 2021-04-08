package com.blps.app.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "BLPS_USER")
@JsonIgnoreProperties({"boss", "booksCreator", "booksBoss", "password", "subordinates", "admin"}) //to avoid cycle
public class User implements Serializable{

    @Id
    private String login;

    private String password;

    private String name;

    private String surname;

    private String passport;

    private boolean admin;
    
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @ManyToOne
    private User boss;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "creator")
    private List<Book> booksCreator;

    @OneToMany(mappedBy = "boss")
    private List<Book> booksBoss;

    @OneToMany(mappedBy = "boss")
    private List<User> subordinates;
}
