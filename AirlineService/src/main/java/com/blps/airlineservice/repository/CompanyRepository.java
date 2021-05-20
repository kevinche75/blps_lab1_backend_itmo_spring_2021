package com.blps.airlineservice.repository;

import com.blps.airlineservice.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends JpaRepository<Company, String>, CrudRepository<Company, String> {
}
