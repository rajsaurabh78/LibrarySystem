package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.DTO.StudentDTO;
import com.exception.AdminException;
import com.exception.FloorException;
import com.exception.SeatException;
import com.exception.ShiftException;
import com.exception.StudentException;
import com.modal.Admin;
import com.modal.Floor;
import com.modal.Shift;
import com.modal.Student;
import com.repository.AdminRepository;
import com.repository.FloorRepository;
import com.repository.ShiftRepository;
import com.repository.studentRepository;
@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private studentRepository studentRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private ShiftRepository shiftRepository;
	
	@Autowired
	private FloorRepository floorRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public List<Student> getAllStudent() {
		
		List<Student> sList=studentRepository.findAll();
		if(sList.isEmpty()) {
			throw new StudentException("No any student found .");
		}else 
			return sList;
	}

	@Override
	public Student getStudentById(Integer id) {

		return studentRepository.findById(id).orElseThrow(()->new StudentException("Inviled UserId/studentId ."));
	}

	@Override
	public Student getStudentBySeatNo(Integer SeatNo) {

		return studentRepository.findBySeatNo(SeatNo).orElseThrow(()->new SeatException("Inviled SeatNo ."));
		
	}

	@Override
	public List<Student> getAllStudentByFloor(Integer floorNo) {
		
		List<Student> sList=new ArrayList<>();
		Optional<Shift> shift=shiftRepository.findById(floorNo);
//		List<Student> studs=shift.get
		return null;
	}

	@Override
	public Integer getAllAvalibleSeats() {

		int seats=0;
		List<Floor> list=floorRepository.findAll();
		for(Floor floor:list) {
			List<Shift> shiftList=floor.getShiftList();
			for(Shift i:shiftList) {
				seats+=i.getSeats();
			}
		}
		if(seats==0) {
			throw new SeatException("Seat not available");
		}
		else
			return seats;
	}

	@Override
	public List<Student> getAllStudentShiftWise(String shift) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeStudent(Integer seatsNo) {

		Optional<Student> opt=studentRepository.findBySeatNo(seatsNo);
		if(opt.isPresent()) {
			studentRepository.delete(opt.get());
			return "Successfully deleted .";
		}else
			throw new StudentException("Inviled seatNo .");
	}

	@Override
	public List<Student> getStudentAreaWise(String address) {

		List<Student> sList=studentRepository.findByAddress(address);
		if(!sList.isEmpty()) {
			return sList;
		}
		else
			throw new StudentException("No record found .");
	}

	@Override
	public List<StudentDTO> getAllStudentWithNoSeatNo() {
		List<StudentDTO> dto=new ArrayList<>();
		List<Student> sList=studentRepository.findAll();
		if(sList.isEmpty()) {
			throw new StudentException("No record found .");
		}
		else {
			for(Student stu:sList) {
				if(stu.getPayment()==false) {
					dto.add(new StudentDTO(stu.getName(), stu.getUserId(),stu.getPayment()));
				}
			}
			return dto;
		}
		
	}

	@Override
	public String studentSeatAllotement(Integer Id) {
		
		Optional<Student> opt=studentRepository.findById(Id);
		if(opt.isPresent()) {
			Student stu=opt.get();
			stu.setSeatNo(1);
			return "Updated "+stu.toString();
		}else
			throw new StudentException("Inviled Student Id .");
	}

	@Override
	public Admin registerAdmin(Admin admin) {
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		return adminRepository.save(admin);
	}

	@Override
	public Admin updateAdmin(Admin admin) {

		Optional<Admin> opt=adminRepository.findById(admin.getId());
		if(opt.isPresent()) {
			Admin ad=opt.get();
			ad.setEmail(admin.getEmail());
			ad.setAddress(admin.getAddress());
			ad.setMobile(admin.getMobile());
			ad.setName(admin.getName());
			ad.setPassword(passwordEncoder.encode(admin.getPassword()));
			return adminRepository.save(ad);
		}
		else
			throw new AdminException("Inviled admin Id .");
	}

	@Override
	public String removeAdmin(Integer id) {

		Optional<Admin> opt=adminRepository.findById(id);
		if(opt.isPresent()) {
			Admin ad=opt.get();
			adminRepository.delete(ad);
			return "Deleted successfully .";
		}
		else
			throw new AdminException("Inviled admin Id .");
	}

	@Override
	public Floor addFloor(Floor floor) {
		
		return floorRepository.save(floor);
		
	}

	@Override
	public Floor updateFloorName(Integer floorNo,String newName) {
		
		Optional<Floor> opt=floorRepository.findById(floorNo);
		if(opt.isEmpty()) {
			throw new FloorException("Inviled floorNo .");
		}else {
			Floor fl=opt.get();
			fl.setName(newName);
			return floorRepository.save(fl);
		}
	}

	@Override
	public String removeFloor(Integer floorNo) {
		
		Optional<Floor> opt=floorRepository.findById(floorNo);
		if(opt.isEmpty()) {
			throw new FloorException("Inviled floorNo .");
		}else {
			Floor fl=opt.get();
			floorRepository.delete(fl);
			return "Floor deleted .";
		}
	}

	@Override
	public Shift addShift(Shift shift) {

		return shiftRepository.save(shift);
		
	}

	@Override
	public Shift updateShift(Shift shift) {
		
		Optional<Shift> opt=shiftRepository.findById(shift.getShiftId());
		if(opt.isPresent()) {
			Shift sft=opt.get();
			sft.setShiftName(shift.getShiftName());
			sft.setSeats(shift.getSeats());
			sft.setEndTime(shift.getEndTime());
			sft.setStartTime(shift.getStartTime());
			return shiftRepository.save(sft);
		}
		else
			throw new ShiftException("Inviled shift Id .");
		
	}

	@Override
	public String removeShift(Integer ShiftId) {
		
		Optional<Shift> opt=shiftRepository.findById(ShiftId);
		if(opt.isPresent()) {
			shiftRepository.delete(opt.get());
			return "Shift deleted .";
		}
		else
			throw new ShiftException("Inviled shift Id .");
		
	}

	@Override
	public String updateSeats(Integer floorNo, Integer newSeats) {

		Optional<Floor> opt=floorRepository.findById(floorNo);
		if(opt.isPresent()) {
			Floor fl=opt.get();
			List<Shift> list=fl.getShiftList();
			for(Shift i:list) {
				i.setSeats(i.getSeats()+newSeats);			
			}
			return "Seats increased by :- "+newSeats;
		}
		else
			throw new FloorException("Inviled floor Id .");
		
	}
		

}
