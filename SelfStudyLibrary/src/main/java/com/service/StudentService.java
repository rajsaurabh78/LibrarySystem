package com.service;

import java.util.List;

import com.modal.Library;
import com.modal.Shift;
import com.modal.Student;

public interface StudentService {
	
	public Student registerStudent(Student student);
	public List<Shift> getAllShiftByFloorNo(Integer floorNo);
	public Library getAllDetails();
	public Student updateStudent(Student student);
	public Student getOwnProfile();
	
}
