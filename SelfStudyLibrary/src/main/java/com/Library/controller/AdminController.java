package com.Library.controller;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Library.DTO.SeatDTO;
import com.Library.DTO.ShiftDTO;
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
import com.Library.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/addlibrary")
	public ResponseEntity<Library> addLibraryController(@Valid @RequestBody Library library){
		Library list=adminService.addLibrary(library);
		return new ResponseEntity<>(list,HttpStatus.CREATED);
	}	
	
	@GetMapping("/librarys")
	public ResponseEntity<List<Library>> allLibraryController(){
		List<Library> list=adminService.allLibrary();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@DeleteMapping("/delLibrary/{lId}")
	public ResponseEntity<String> deleteLibraryController(@Valid @PathVariable("lId") Integer lId){
		String list=adminService.deleteLibrary(lId);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PatchMapping("/library/{lId}")
	public ResponseEntity<String> updateLibraryController(@Valid @PathVariable("lId")Integer lId ,@Valid @RequestBody UpdateLibraryDTO updateLibraryDTO){
		String list=adminService.updateLibrary(lId, updateLibraryDTO);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@GetMapping("/students")
	public ResponseEntity<List<Student>> getAllStudentInSortingOrderController(@Valid @RequestParam("field")String field,@Valid @RequestParam("direction")String direction){
		List<Student> list=adminService.getAllStudentInSortingOrder(field,direction);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/stu/{pageNo}/{pageSize}")
	public ResponseEntity<List<Student>> getAllSortedStudentWithPaginationController(
			@Valid @PathVariable("pageNo")Integer pageNo,@Valid @PathVariable("pageSize")Integer pageSize,
			@Valid @RequestParam String field,@Valid @RequestParam String direction){
		List<Student> list=adminService.getAllSortedStudentWithPagination(field, direction, pageNo, pageSize);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentByIdController(@Valid @PathVariable("id")Integer id){
		Student list=adminService.getStudentById(id);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/getstudent/{seatNo}")
	public ResponseEntity<Student> getStudentBySeatNoController(@Valid @PathVariable("seatNo")Integer seatNo){
		Student list=adminService.getStudentBySeatNo(seatNo);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	//need
	@GetMapping("/studentfl/{floorNo}")
	public ResponseEntity<List<Student>> getAllStudentByFloorController(@Valid @PathVariable("floorNo")Integer floorNo){
		List<Student> list=adminService.getAllStudentByFloor(floorNo);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	//frontend pending
	@GetMapping("/seats")
	public ResponseEntity<List<SeatDTO>> getAllAvalibleSeatsController(){
		List<SeatDTO> i=adminService.getAllAvalibleSeats();
		return new ResponseEntity<>(i,HttpStatus.OK);
	}
	
	@GetMapping("/studentshift/{shiftNo}")
	public ResponseEntity<List<Student>> getAllStudentShiftWiseController(@Valid @PathVariable("shiftNo")Integer shiftNo){
		List<Student> list=adminService.getAllStudentShiftWise(shiftNo);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@DeleteMapping("/studentdel/{userId}")
	public ResponseEntity<String> removeStudentController(@Valid @PathVariable("userId")Integer userId){
		String list=adminService.removeStudent(userId);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/studentlist")
	public ResponseEntity<List<Student>> getStudentAreaWiseController(@Valid @RequestParam("address")String address){
		List<Student> list=adminService.getStudentAreaWise(address);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}

	@GetMapping("/studentno")
	public ResponseEntity<List<StudentDTO>> getAllStudentWithNoSeatNoController(){
		List<StudentDTO> list=adminService.getAllStudentWithNoSeatNo();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/studentnopay")
	public ResponseEntity<List<Student>> allStudentWithNoPaymentController(){
		List<Student> list=adminService.allStudentWithNoPayment();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PutMapping("/uppay/{id}")
	public ResponseEntity<String> updatePaymentController(@Valid @PathVariable("id")Integer id){
		String list=adminService.updatePayment(id);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/admins/{id}")
	public ResponseEntity<Admin> getAdminByIdController(@Valid @PathVariable("id")Integer id){
		Admin list=adminService.getAdminById(id);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/admin/{pageNo}/{pageSize}")
	public ResponseEntity<List<Admin>> getAllAdminController(
			@Valid @PathVariable("pageNo")Integer pageNo,@Valid @PathVariable("pageSize")Integer pageSize){
		List<Admin> list=adminService.allAdmin(pageNo, pageSize);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/floors/{lId}")
	public ResponseEntity<List<Floor>> getFloorbyLidController(@Valid @PathVariable("lId")Integer lId){
		List<Floor> list=adminService.getFloorbyLid(lId);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/shifts/{fId}")
	public ResponseEntity<List<Shift>> getShiftsByFloorController(@Valid @PathVariable("fId")Integer fId){
		List<Shift> list=adminService.getShiftsByFloor(fId);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/seats/{shiftId}")
	public ResponseEntity<List<Seat>> getSeatsByShiftController(@Valid @PathVariable("shiftId")Integer shiftId){
		List<Seat> list=adminService.getSeatsByShift(shiftId);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	//need
	@GetMapping("/studentseat/{id}")
	public ResponseEntity<String> studentSeatAllotementController(@Valid @PathVariable("id")Integer id){
		String list=adminService.studentSeatAllotement(id);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/studentseats/{id}")
	public ResponseEntity<String> seatAllotementManualController(@Valid @PathVariable("id")Integer id,@Valid @RequestParam("shiftName")String shiftName){
		String list=adminService.seatAllotementManual(id, shiftName.toUpperCase());
		return new ResponseEntity<>(list,HttpStatus.OK);
	}

	
//		{
//		  "name":"Raj",
//		  "email":"raj@gmail.com",
//		  "password":"Raj@12345",
//		  "address":"Hajipur",
//		  "DOB":"2004-10-20",
//		  "mobile":"9912343434"
//		}
	
	@PostMapping("/admin")
	public ResponseEntity<Admin> addAdminController(@Valid @RequestBody Admin admin){
		Admin list=adminService.addAdmin(admin);
		return new ResponseEntity<>(list,HttpStatus.CREATED);
	}
	
	@PatchMapping("/upadmin")
	public ResponseEntity<Admin> updateAdminController(@Valid @RequestBody UpdateDetailsDTO updateDetailsDTO) throws LoginException{
		Admin list=adminService.updateAdmin(updateDetailsDTO);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@DeleteMapping("/deladmin/{id}")
	public ResponseEntity<String> removeAdminController(@Valid @PathVariable("id")Integer id){
		String list=adminService.removeAdmin(id);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@DeleteMapping("/seats/{seatNo}")
	public ResponseEntity<String> deleteSeatByIdController(@Valid @PathVariable("seatNo")Integer seatNo){
		String list=adminService.deleteSeatById(seatNo);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/addfloor/{lId}")
	public ResponseEntity<Floor> addFloorController(@Valid @RequestBody Floor floor,@Valid @PathVariable("lId") Integer lId){
		Floor list=adminService.addFloor(floor,lId);
		return new ResponseEntity<>(list,HttpStatus.CREATED);
	}	
	
	@PutMapping("/upfloor/{floorNo}")
	public ResponseEntity<Floor> updateFloorNameController(@Valid @PathVariable("floorNo") Integer floorNo ,@Valid @RequestParam("name") String name){
		Floor list=adminService.updateFloorName(floorNo, name);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@DeleteMapping("/delfloor/{floorNo}")
	public ResponseEntity<String> removeFloorController(@Valid @PathVariable("floorNo") Integer floorNo){
		String list=adminService.removeFloor(floorNo);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@PostMapping("/addshift/{floorNo}")
	public ResponseEntity<Shift> addShiftController(@Valid @RequestBody ShiftDTO ShiftDto,@Valid @PathVariable("floorNo") Integer floorNo){
		Shift list=adminService.addShift(ShiftDto,floorNo);
		return new ResponseEntity<>(list,HttpStatus.CREATED);
	}	
	
	
	@PatchMapping("/upshift")
	public ResponseEntity<Shift> updateShiftController(@Valid @RequestBody UpdateShiftDTO updateShiftDTO){
		Shift list=adminService.updateShift(updateShiftDTO);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	//check one last
	@DeleteMapping("/delshift/{shiftId}")
	public ResponseEntity<String> removeShiftController(@Valid @PathVariable("shiftId") Integer shiftId){
		String list=adminService.removeShift(shiftId);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@PostMapping("/seats/{shiftNo}")
	public ResponseEntity<List<Seat>> addSeatController(@Valid @PathVariable("shiftNo") Integer shiftNo,@Valid @RequestParam("noOfSeats")Integer noOfSeats){
		List<Seat> list=adminService.addSeat(shiftNo,noOfSeats);
		return new ResponseEntity<>(list,HttpStatus.CREATED);
	}
	
	@PutMapping("/upseat/{seatNo_Or_UserId}")
	public ResponseEntity<String> removeStudentSeatController(@Valid @PathVariable("seatNo_Or_UserId") Integer seatNo_Or_UserId){
		String list=adminService.removeStudentSeat(seatNo_Or_UserId);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	

}
