package com.blps.airlineservice.repository;

import com.blps.airlineservice.model.Company;
import com.blps.airlineservice.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {

    @Query(nativeQuery = true, value = "select * from blps_log where company_name = ?1")
    List<Log> findByCompanyName(String companyName);

    @Query(nativeQuery = true, value = "select * from blps_log where company_name = ?1 and create_time > ?2")
    List<Log> findByCompanyNameAndCreateTimeAfter(String companyName, Date timeAfter);

    @Query(nativeQuery = true, value = "select * from blps_log where company_name = ?1 and create_time < ?2")
    List<Log> findByCompanyNameAndCreateTimeBefore(String companyName, Date timeBefore);

    @Query(nativeQuery = true, value = "select * from blps_log where company_name = ?1 and create_time > ?2 and create_time < ?3")
    List<Log> findByCompanyNameAndCreateTimeAfterAndCreateTimeBefore(String companyName, Date timeAfter, Date timeBefore);
}
