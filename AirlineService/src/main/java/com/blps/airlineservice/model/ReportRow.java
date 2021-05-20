package com.blps.airlineservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class ReportRow {

    String boss_login;
    String creator_login;

    Date creation_time;
    BookStatus status;

    Date arrival_time;
    Date departure_time;
    String airline;

    String place_from;
    String place_to;

    Double cost;
}
