package com.blps.app.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "blps_report")
public class Report {
    @Id
    private long id;

    @Lob
    private byte[] content;
}
