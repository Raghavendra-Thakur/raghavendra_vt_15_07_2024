package com.vt.project.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vt.project.model.Student;
import com.vt.project.service.StudentService;
import com.vt.project.utils.LogicEnum;
import com.vt.project.utils.Permission;
import com.vt.project.utils.PermissionsEnum;

@RestController
@RequestMapping(value = "student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Permission(permissions = { PermissionsEnum.AllowRead, PermissionsEnum.AllowWrite,
			PermissionsEnum.AllowUpdate }, type = LogicEnum.Any)
	@GetMapping(value = "/getall")
	public List<Student> getAllStudent() {

		List<Student> allStudent = studentService.getAllStudent();

		return allStudent;
	}

	@Permission(permissions = { PermissionsEnum.AllowRead, PermissionsEnum.AllowWrite,
			PermissionsEnum.AllowUpdate }, type = LogicEnum.All)
	@PostMapping(value = "/create")
	public Student createStudent(@RequestBody Student student) {

		Student student2 = studentService.createStudent(student);

		return student2;
	}

}
