package com.Library.service;

import java.util.List;

import com.Library.modal.Library;
import com.Library.modal.Shift;
import com.Library.modal.Student;

public interface StudentService {
	
	public Student registerStudent(Student student);
	public List<Shift> getAllShiftByFloorNo(Integer floorNo);
	public List<Library> getAllDetails();
	public Student updateStudent(Student student);
	public Student getOwnProfile();
	
}
