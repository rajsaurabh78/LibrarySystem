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
	
	@GetMapping("/studentfloor/{floorNo}")
	public ResponseEntity<List<Student>> getAllStudentByFloorController(@Valid @PathVariable("floorNo")Integer floorNo){
		List<Student> list=adminService.getAllStudentByFloor(floorNo);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/student")
	public ResponseEntity<Integer> getAllAvalibleSeatsController(){
		Integer i=adminService.getAllAvalibleSeats();
		return new ResponseEntity<>(i,HttpStatus.OK);
	}
	
	@GetMapping("/studentshift/{shiftNo}")
	public ResponseEntity<List<Student>> getAllStudentShiftWiseController(@Valid @PathVariable("shiftNo")Integer shiftNo){
		List<Student> list=adminService.getAllStudentShiftWise(shiftNo);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@DeleteMapping("/studentdel/{seatNo}")
	public ResponseEntity<String> removeStudentController(@Valid @PathVariable("seatNo")Integer seatNo){
		String list=adminService.removeStudent(seatNo);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/studentlist")
	public ResponseEntity<List<Student>> getStudentAreaWiseController(@Valid @RequestParam("floorNo")String address){
		List<Student> list=adminService.getStudentAreaWise(address);
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
	
	@DeleteMapping("/deladmin")
	public ResponseEntity<String> removeAdminController(@Valid @PathVariable("Id")Integer id){
		String list=adminService.removeAdmin(id);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	
	
	@PostMapping("/addfloor")
	public ResponseEntity<Floor> addFloorController(@Valid @RequestBody Floor floor){
		Floor list=adminService.addFloor(floor);
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
	
	@PostMapping("/addseat")
	public ResponseEntity<Seat> addSeatController(@Valid @RequestBody Seat seat){
		Seat list=adminService.addSeat(seat);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PutMapping("/upseat/{seatNo}")
	public ResponseEntity<String> removeStudentSeatController(@Valid @PathVariable("seatNo") Integer seatNo){
		String list=adminService.removeStudentSeat(seatNo);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}	

}
