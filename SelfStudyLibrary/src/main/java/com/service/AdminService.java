package com.service;

import java.util.List;

import com.DTO.StudentDTO;
import com.modal.Admin;
import com.modal.Floor;
import com.modal.Shift;
import com.modal.Student;

public interface AdminService {
	
	public List<Student> getAllStudent();
	public Student getStudentById(Integer id);
	public Student getStudentBySeatNo(Integer SeatNo);
	public List<Student> getAllStudentByFloor(Integer floorNo);
	public Integer getAllAvalibleSeats();
	public List<Student> getAllStudentShiftWise(String shift);
	public String removeStudent(Integer seatsNo);
	public List<Student> getStudentAreaWise(String address);
	public List<StudentDTO> getAllStudentWithNoSeatNo();
	public String studentSeatAllotement(Integer Id);
	
	public Admin registerAdmin(Admin admin);
	public Admin updateAdmin(Admin admin);
	public String removeAdmin(Integer id);
	
	public Floor addFloor(Floor floor);
	public Floor updateFloorName(Integer floorNo,String newName);
	public String removeFloor(Integer floorNo);
	
	public Shift addShift(Shift shift);
	public Shift updateShift(Shift shift);
	public String removeShift(Integer ShiftId);
	public String updateSeats(Integer floorNo,Integer newSeats);

}
