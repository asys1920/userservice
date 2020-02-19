package com.asys1920.service.repository;


import com.asys1920.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface UserRepository extends JpaRepository<User, Long> {


    @Modifying
    @Query("update User u set u.expirationDateDriversLicense = :newExpirationDate where u.id = :id")
    void setExpirationDateDriversLicense(@Param("id") Long id, @Param("newExpirationDate") Instant newExpirationDate);
}
