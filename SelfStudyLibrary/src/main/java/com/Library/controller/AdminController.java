package com.Library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Library.DTO.StudentDTO;
import com.Library.modal.Admin;
import com.Library.modal.Floor;
import com.Library.modal.Library;
import com.Library.modal.Seat;
import com.Library.modal.Shift;
import com.Library.modal.Student;
import com.Library.service.AdminService;

import jakarta.validation.Valid;

@RestController
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/addlibrary")
	public ResponseEntity<Library> addLibraryController(@Valid @RequestBody Library library){
		Library list=adminService.addLibrary(library);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@GetMapping("/students")
	public ResponseEntity<List<Student>> getAllStudentController(){
		List<Student> list=adminService.getAllStudent();
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
	
	@GetMapping("/studentfl/{floorNo}")
	public ResponseEntity<List<Student>> getAllStudentByFloorController(@Valid @PathVariable("floorNo")Integer floorNo){
		List<Student> list=adminService.getAllStudentByFloor(floorNo);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/student")
	public ResponseEntity<List<Seat>> getAllAvalibleSeatsController(){
		List<Seat> i=adminService.getAllAvalibleSeats();
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
	
	@GetMapping("/studentseat/{id}")
	public ResponseEntity<String> studentSeatAllotementController(@Valid @PathVariable("id")Integer id){
		String list=adminService.studentSeatAllotement(id);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/admin")
	public ResponseEntity<Admin> registerAdminController(@Valid @RequestBody Admin admin){
		Admin list=adminService.registerAdmin(admin);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PutMapping("/upadmin")
	public ResponseEntity<Admin> updateAdminController(@Valid @RequestBody Admin admin){
		Admin list=adminService.updateAdmin(admin);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@DeleteMapping("/deladmin/{id}")
	public ResponseEntity<String> removeAdminController(@Valid @PathVariable("id")Integer id){
		String list=adminService.removeAdmin(id);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@PostMapping("/addfloor/{lId}")
	public ResponseEntity<Floor> addFloorController(@Valid @RequestBody Floor floor,@Valid @PathVariable("lId") Integer lId){
		Floor list=adminService.addFloor(floor,lId);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@PutMapping("/upfloor/{floorNo}/{newName}")
	public ResponseEntity<Floor> updateFloorNameController(@Valid @PathVariable("floorNo") Integer floorNo ,@Valid @PathVariable("newName") String newName){
		Floor list=adminService.updateFloorName(floorNo, newName);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@DeleteMapping("/delfloor/{floorNo}")
	public ResponseEntity<String> removeFloorController(@Valid @PathVariable("floorNo") Integer floorNo){
		String list=adminService.removeFloor(floorNo);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@PostMapping("/addshift/{floorNo}")
	public ResponseEntity<Shift> addShiftController(@Valid @RequestBody Shift Shift,@Valid @PathVariable("floorNo") Integer floorNo){
		Shift list=adminService.addShift(Shift,floorNo);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	
	@PutMapping("/upshift")
	public ResponseEntity<Shift> updateShiftController(@Valid @RequestBody Shift shift){
		Shift list=adminService.updateShift(shift);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@DeleteMapping("/delshift/{shiftId}")
	public ResponseEntity<String> removeShiftController(@Valid @PathVariable("shiftId") Integer shiftId){
		String list=adminService.removeShift(shiftId);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@PostMapping("/addseat/{shiftNo}")
	public ResponseEntity<Seat> addSeatController(@Valid @PathVariable Integer shiftNo){
		Seat list=adminService.addSeat(shiftNo);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PutMapping("/upseat/{seatNo_Or_UserId}")
	public ResponseEntity<String> removeStudentSeatController(@Valid @PathVariable("seatNo_Or_UserId") Integer seatNo_Or_UserId){
		String list=adminService.removeStudentSeat(seatNo_Or_UserId);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	

}
