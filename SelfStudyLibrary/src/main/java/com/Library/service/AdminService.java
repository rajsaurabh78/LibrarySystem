package com.Library.service;

import java.util.List;

import com.Library.DTO.StudentDTO;
import com.Library.modal.Admin;
import com.Library.modal.Floor;
import com.Library.modal.Seat;
import com.Library.modal.Shift;
import com.Library.modal.Student;

public interface AdminService {
	
	public List<Student> getAllStudent();
	public Student getStudentById(Integer id);
	public Student getStudentBySeatNo(Integer SeatNo);
	public List<Student> getAllStudentByFloor(Integer floorNo);
	public Integer getAllAvalibleSeats();
	public List<Student> getAllStudentShiftWise(Integer shiftNo);
	public String removeStudent(Integer seatsNo);
	public List<Student> getStudentAreaWise(String address);
	//cont add 
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
	//public String updateSeats(Integer floorNo,Integer newSeats);
	
	public Seat addSeat(Seat seat);
	public String removeStudentSeat(Integer seatNo);

}
