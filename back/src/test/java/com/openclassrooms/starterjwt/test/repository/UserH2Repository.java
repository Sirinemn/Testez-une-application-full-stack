package com.openclassrooms.starterjwt.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.starterjwt.models.User;


public interface UserH2Repository extends JpaRepository<User,Long>{


}
