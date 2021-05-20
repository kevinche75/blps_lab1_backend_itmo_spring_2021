package com.blps.airlineservice.repository;

import com.blps.airlineservice.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
