package com.Library.service;

import java.time.LocalDate;
import java.util.List;

import javax.security.auth.login.LoginException;

import com.Library.DTO.UpdateDetailsDTO;
import com.Library.modal.Library;
import com.Library.modal.Shift;
import com.Library.modal.Student;

public interface StudentService {
	
	public Student registerStudent(Student student);
	public List<Shift> getAllShiftByFloorNo(Integer floorNo);
	public List<Library> getAllDetails();
	public Student updateStudent(UpdateDetailsDTO updateDetailsDTO)throws LoginException;
	public Student getOwnProfile()throws LoginException;
	public String forgetPassword(String email,LocalDate dob,String password);
//	public Admin getAdminByMobile(String mobile);
}
