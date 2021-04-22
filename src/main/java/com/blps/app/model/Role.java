package com.blps.app.model;

import com.blps.app.secure.RoleEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "blps_role")
public class Role {

    public Role() {}

    public Role(RoleEnum role){
        name = role;
    }

    @Id
    private RoleEnum name;
}
