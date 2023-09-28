package com.Library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Library.modal.Student;
import com.Library.repository.studentRepository;
@RestController
public class LoginController {
	
	@Autowired
	private studentRepository studentRepository;
	
	@GetMapping("/signIn")
	public ResponseEntity<Student> getLoggedInCustomerDetailsHandler(Authentication auth){
		
		Student stu=studentRepository.findByEmail(auth.getName()).orElseThrow(()-> new BadCredentialsException("Invalid Username or password"));
		return new ResponseEntity<>(stu,HttpStatus.ACCEPTED);
		
	}
	

}
