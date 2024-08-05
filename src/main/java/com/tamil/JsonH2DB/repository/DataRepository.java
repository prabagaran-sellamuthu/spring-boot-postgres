package com.tamil.JsonH2DB.repository;

import com.tamil.JsonH2DB.entity.ApplicationUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<ApplicationUser, Long> {
}