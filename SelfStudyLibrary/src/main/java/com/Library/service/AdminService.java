package com.Library.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.security.auth.login.LoginException;

import com.Library.DTO.SeatDTO;
import com.Library.DTO.ShiftDTO;
import com.Library.DTO.ShiftStudentDTO;
import com.Library.DTO.StudentDTO;
import com.Library.DTO.UpdateDetailsDTO;
import com.Library.DTO.UpdateLibraryDTO;
import com.Library.DTO.UpdateShiftDTO;
import com.Library.modal.Admin;
import com.Library.modal.Floor;
import com.Library.modal.Library;
import com.Library.modal.Seat;
import com.Library.modal.Shift;
import com.Library.modal.Student;

public interface AdminService {
	
	public List<Admin> allAdmin(Integer pageNo,Integer pageSize);
	public Admin getAdminById(Integer id);
	public List<Student> getAllStudentInSortingOrder(String field,String direction);
	public List<Student> getAllSortedStudentWithPagination(String field,String direction,Integer pageNo,Integer pageSize);
	public Student getStudentById(Integer id);
	public Student getStudentBySeatNo(Integer SeatNo);
	public Set<Student> getAllStudentByFloor(Integer floorNo);
	public List<SeatDTO> getAllAvalibleSeats();
	public List<Student> getAllStudentShiftWise(String shift);
	public List<Student> getAllStudentByShiftId(Integer shiftId);
	public String removeStudent(Integer userId);
	public List<Student> getStudentAreaWise(String address);
	public String forgetPassword(String mobile,LocalDate dob,String password);
	//cont add 
	public List<StudentDTO> getAllStudentWithNoSeatNo();
	public List<Student> allStudentWithNoPayment();
	public String updatePayment(Integer id);
	public String studentSeatAllotement(Integer Id);
	public String seatAllotementManual(Integer id);
	
	public Admin addAdmin(Admin admin);
	public Admin updateAdmin(UpdateDetailsDTO updateDetailsDTO)throws LoginException;
	public String removeAdmin(Integer id);
	
	public List<Floor> getFloorbyLid(Integer lId);
	public Floor addFloor(Floor floor,Integer libraryId);
	public Floor updateFloorName(Integer floorNo,String newName);
	public String removeFloor(Integer floorNo);
	
	public Shift addShift(ShiftDTO shiftDto,Integer floorNo);
	public Shift updateShift(UpdateShiftDTO updateShiftDTO);
	public String removeShift(Integer hiftId);
	public List<Shift>getShiftsByFloor(Integer fId);
	
	public List<ShiftStudentDTO> getSeatsByShift(Integer shiftId);
	public List<Seat> addSeat(Integer shiftNo,Integer noOfSeats);
	public String removeStudentSeat(Integer seatNo_Or_UserId);
	public Library addLibrary (Library library);
	public List<Library> allLibrary();
	public String updateLibrary(Integer lId,UpdateLibraryDTO updateLibraryDTO);
	public String deleteLibrary(Integer lId);
	public String deleteSeatById(Integer seatNo);
	public Admin profile();

}
