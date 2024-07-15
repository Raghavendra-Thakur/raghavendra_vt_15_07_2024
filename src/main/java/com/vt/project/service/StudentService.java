package com.vt.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vt.project.model.Student;
import com.vt.project.repository.studentRepo;

@Service
public class StudentService {

	@Autowired
	private studentRepo studentRepo;
	
	
	public List<Student> getAllStudent() {
		
		List<Student> allStudents = studentRepo.findAll();
		
		return allStudents;
		
	}
	
	
	public Student createStudent(Student student) {
		
		studentRepo.save(student);
		
		return student;
	}
	
	
}
