package com.blps.airlineservice.repository;

import com.blps.airlineservice.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
