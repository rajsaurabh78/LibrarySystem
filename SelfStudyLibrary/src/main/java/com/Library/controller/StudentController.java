package com.Library.controller;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Library.DTO.UpdateDetailsDTO;
import com.Library.modal.Library;
import com.Library.modal.Shift;
import com.Library.modal.Student;
import com.Library.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/allshift/{floorNo}")
	public ResponseEntity<List<Shift>> getAllShiftByFloorNoController(@Valid @PathVariable("floorNo") Integer floorNo){
		
		List<Shift> sft=studentService.getAllShiftByFloorNo(floorNo);
		return new ResponseEntity<>(sft,HttpStatus.OK);
		
	}
	
	@PatchMapping("/student")
	public ResponseEntity<Student> updateStudentController(@Valid @RequestBody UpdateDetailsDTO updateDetailsDTO) throws LoginException{
		
		Student stu=studentService.updateStudent(updateDetailsDTO);
		return new ResponseEntity<>(stu,HttpStatus.OK);
		
	}
	
	@GetMapping("/allDetails")
	public ResponseEntity<List<Library>> getAllDetailsController(){
		
		List<Library> sft=studentService.getAllDetails();
		return new ResponseEntity<>(sft,HttpStatus.OK);
		
	}
	
	@GetMapping("/profile")
	public ResponseEntity<Student> getOwnProfileController() throws LoginException{
		
		Student st=studentService.getOwnProfile();
		return new ResponseEntity<>(st,HttpStatus.OK);
		
	}

}
