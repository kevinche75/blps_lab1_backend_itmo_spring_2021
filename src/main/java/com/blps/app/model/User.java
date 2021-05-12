package com.blps.app.model;
import com.blps.app.securty.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "BLPS_USER")
@JsonIgnoreProperties({"boss", "booksCreator", "booksBoss", "password"}) //to avoid cycle
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

    @OneToMany(mappedBy = "creator")
    private List<Book> booksCreator;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "blps_role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "boss")
    private List<Book> booksBoss;
}
