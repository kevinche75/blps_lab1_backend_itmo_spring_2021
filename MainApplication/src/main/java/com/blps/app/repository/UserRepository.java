package com.blps.app.repository;

import com.blps.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(nativeQuery = true, value = "select * from blps_user where boss_login = ?1")
    List<User> findSubordinates(String login);
}
