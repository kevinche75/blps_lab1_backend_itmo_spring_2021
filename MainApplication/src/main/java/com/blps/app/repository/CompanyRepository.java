package com.blps.app.repository;

import com.blps.app.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends JpaRepository<Company, String>, CrudRepository<Company, String> {
}
