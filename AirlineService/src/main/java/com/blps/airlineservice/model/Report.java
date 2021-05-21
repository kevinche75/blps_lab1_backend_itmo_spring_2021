package com.blps.airlineservice.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "blps_report")
public class Report {
    @Id
    private long id;

    @Lob
    private byte[] content;
}
