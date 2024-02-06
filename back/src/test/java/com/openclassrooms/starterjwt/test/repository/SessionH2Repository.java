package com.openclassrooms.starterjwt.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.starterjwt.models.Session;

public interface SessionH2Repository extends JpaRepository<Session,Long>{

}
