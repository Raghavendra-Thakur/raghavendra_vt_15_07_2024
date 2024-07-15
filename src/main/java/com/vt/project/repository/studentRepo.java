package com.vt.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vt.project.model.Student;

public interface studentRepo extends JpaRepository<Student, Integer> {
}
