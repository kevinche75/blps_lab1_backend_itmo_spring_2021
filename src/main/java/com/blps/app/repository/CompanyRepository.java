package com.blps.app.repository;

import com.blps.app.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String>{
}
