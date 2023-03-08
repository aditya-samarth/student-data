package com.springboot.studentdata.repository;

import com.springboot.studentdata.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // All CRUD Database Methods


}
