package com.blps.airlineservice.repository;

import com.blps.airlineservice.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
