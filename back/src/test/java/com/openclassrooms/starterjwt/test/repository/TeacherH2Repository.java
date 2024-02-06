package com.openclassrooms.starterjwt.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.starterjwt.models.Teacher;

public interface TeacherH2Repository extends JpaRepository<Teacher,Long>{

}
