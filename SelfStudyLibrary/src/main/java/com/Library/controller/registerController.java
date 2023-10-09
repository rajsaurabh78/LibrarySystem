package com.Library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Library.modal.Admin;
import com.Library.modal.Student;
import com.Library.service.AdminService;
import com.Library.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/register")
public class registerController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/addStudent")
	public ResponseEntity<Student> registerStudentController(@Valid @RequestBody Student student){
		
		Student stu=studentService.registerStudent(student);
		return new ResponseEntity<>(stu,HttpStatus.CREATED);
		
	}
}
