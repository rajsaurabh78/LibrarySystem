package com.Library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Library.DTO.StudentDTO;
import com.Library.exception.AdminException;
import com.Library.exception.FloorException;
import com.Library.exception.LibraryException;
import com.Library.exception.SeatException;
import com.Library.exception.ShiftException;
import com.Library.exception.StudentException;
import com.Library.modal.Admin;
import com.Library.modal.Floor;
import com.Library.modal.Library;
import com.Library.modal.Seat;
import com.Library.modal.Shift;
import com.Library.modal.Student;
import com.Library.repository.AdminRepository;
import com.Library.repository.FloorRepository;
import com.Library.repository.LibraryRepository;
import com.Library.repository.SeatRepository;
import com.Library.repository.ShiftRepository;
import com.Library.repository.studentRepository;
@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private studentRepository studentRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private ShiftRepository shiftRepository;
	
	@Autowired
	private LibraryRepository libraryRepository;
	
	@Autowired
	private FloorRepository floorRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override//work
	public List<Student> getAllStudent() {
		
		List<Student> sList=studentRepository.findAll();
		if(sList.isEmpty()) {
			throw new StudentException("No any student found .");
		}else 
			return sList;
	}

	@Override//work
	public Student getStudentById(Integer id) {

		return studentRepository.findById(id).orElseThrow(()->new StudentException("Inviled UserId/studentId ."));
	}

	@Override//work
	public Student getStudentBySeatNo(Integer SeatNo) {

		Optional<Seat> seat=seatRepository.findById(SeatNo);
		if(seat.isPresent()) {
			Seat st=seat.get();
			if(st.getStudent()!=null) {
				return st.getStudent();
			}else {
				throw new SeatException("Seat is empty .");
			}
		}else {
			throw new SeatException("Inviled seat no .");
		}
		
	}

	@Override//work
	public List<Student> getAllStudentByFloor(Integer floorNo) {
	
		List<Student> studs=studentRepository.findAll();
		if(studs.size() >0 ) {
			List<Student> sList=new ArrayList<>();
			for(Student s:studs) {
				if(s.getFloor()==floorNo && s.getSeats()!=null) {
					sList.add(s);
				}
			}
			
		if(sList.size()==0) {
			throw new StudentException("Student not avalible ./ Inviled floor No . ");
		}else
			return sList;
		}else
			throw new StudentException("Data not avalible .");
		
	}

	@Override//work
	public List<Seat> getAllAvalibleSeats() {

		List<Seat> sList=new ArrayList<>();
		List<Floor> list=floorRepository.findAll();
		for(Floor floor:list) {
			List<Shift> shiftList=floor.getShiftList();
			for(Shift s:shiftList) {
				List<Seat> seats= s.getSeatList();
				for(Seat st:seats) {
					if(st.getStudent()==null) {
						sList.add(st);								
					}
				}
			
			}
		}
		if(sList.size()==0) {
			throw new SeatException("Seat not available");
		}
		else
			return sList;
	}

	@Override//work
	public List<Student> getAllStudentShiftWise(Integer shiftNo) {

		Optional<Shift> opt=shiftRepository.findById(shiftNo);
		if(opt.isPresent()) {
			List<Student> studs=new ArrayList<>();
			Shift sft= opt.get();
			List<Seat> seats=sft.getSeatList();
			for(Seat s:seats) {
				if(s.getStudent()!=null) {
					studs.add(s.getStudent());
				}
			}
			if(studs.size()==0 ) {
				throw new StudentException("No data present .");
			}else
				return studs;
		}else
			throw new StudentException("Inviled seatNo .");
		
	}

	@Override//work
	public String removeStudent(Integer userId) {
		
		Optional<Student> opt=studentRepository.findById(userId);
		if(opt.isPresent()&& opt.get().getSeats()==null){
			Student stu=opt.get();
			studentRepository.delete(stu);
			return "Successfully deleted .";
		}else
			throw new StudentException("Inviled userId ./ Student register with a seat .");
		
	}

	@Override//work
	public List<Student> getStudentAreaWise(String address) {

		List<Student> sList=studentRepository.findByAddress(address);
		if(!sList.isEmpty()) {
			return sList;
		}
		else
			throw new StudentException("No record found .");
	}

	@Override//work
	public List<StudentDTO> getAllStudentWithNoSeatNo() {
		List<StudentDTO> dto=new ArrayList<>();
		List<Student> sList=studentRepository.findAll();
		if(sList.isEmpty()) {
			throw new StudentException("No record found .");
		}
		else {
			for(Student stu:sList) {
				if(stu.getPayment()==true&&stu.getSeats()==null){
					dto.add(new StudentDTO(stu.getName(), stu.getUserId(),stu.getPayment()));
				}
			}
			return dto;
		}
	}

	@Override//not_work_proper 
	public String studentSeatAllotement(Integer Id) {
		
		Optional<Student> opt=studentRepository.findById(Id);
		if(opt.isPresent()&& opt.get().getPayment()==true ){
			Student stu=opt.get();
			String str=stu.getShift();
			if(str.equals("FIRST")) {
				Seat seat=seatRepository.getFirstShiftSeat();
				if(seat!=null) {
					List<Seat> sl=new ArrayList<>();
					seat.setStudent(stu);
					sl.add(seat);
					stu.setSeats(sl);
					stu.setShift(null);
					studentRepository.save(stu);			
					return "Registered in first shift.";
				}else {
					throw new SeatException("Seat not avalible.");
				}	
				
			}else if(str.equals("SECOND")) {
				
				Seat seat=seatRepository.getSecondSeat();
				if(seat!=null) {
					List<Seat> sl=new ArrayList<>();
					seat.setStudent(stu);
					sl.add(seat);
					stu.setShift(null);
					stu.setSeats(sl);
					studentRepository.save(stu);
					return "Registered in second shift.";
				}else {
					throw new SeatException("Seat not avalible.");
				}
				
			}else if(str.equals("THIRD")) {
				
				Seat seat=seatRepository.getThirdSeat();
				if(seat!=null) {
					List<Seat> sl=new ArrayList<>();
					seat.setStudent(stu);
					sl.add(seat);
					stu.setShift(null);
					stu.setSeats(sl);
					studentRepository.save(stu);
					return "Registered in third shift.";
				}else {
					throw new SeatException("Seat not avalible.");
				}
				
			}else if(str.equals("ALL")) {
				
				Seat seat=seatRepository.getFirstShiftSeat();
				Seat seat1=seatRepository.getFirstShiftSeat();
				Seat seat2=seatRepository.getFirstShiftSeat();
				if(seat!=null&&seat1!=null&&seat2!=null) {
					
					List<Seat> sl=new ArrayList<>();
					seat.setStudent(stu);
					seat1.setStudent(stu);
					seat2.setStudent(stu);
					sl.add(seat);
					sl.add(seat1);
					sl.add(seat2);
		//			stu.setShift(null);
					stu.setSeats(sl);
				//	studentRepository.save(stu);
					seatRepository.saveAll(sl);
					return "Registered with all shifts .";
					
				}else {
					throw new SeatException("Seat not avalible.");
				}
					
			}else {
				
				throw new ShiftException("Inviled Shift name.");
				
			}
		}else
			throw new StudentException("Inviled Student Id / Payment is not clear.");
		
	}


	@Override//work
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

	@Override//work
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

	@Override//work
	public Floor addFloor(Floor floor,Integer libraryId) {
		Optional<Library> labs=libraryRepository.findById(libraryId);
		if(labs.isPresent()) {
			
			floor.setLibrary(labs.get());
			List<Shift> shifts=floor.getShiftList();
			for(Shift s:shifts) {
				s.setFloor(floor);
			}
			return floorRepository.save(floor);
		}else {
			throw new LibraryException("Inviled library Id .");
		}
		
		
	}

	@Override//work
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

	@Override//work
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

	@Override//work
	public Shift addShift(Shift shift,Integer floorNo) {	
		Optional<Floor> opt=floorRepository.findById(floorNo);
		List<Shift> sl=new ArrayList<>();
		sl.add(shift);
		if(opt.isPresent()) {
			Floor fl=opt.get();
			fl.setShiftList(sl);
			shift.setFloor(fl);
			List<Seat> sList=shift.getSeatList();
			for(Seat i:sList) {				i.setFloor(floorNo);
				i.setShift(shift);
			}
			return shiftRepository.save(shift);
		}else
			throw new FloorException("Inviled floorNo .");
	}

	@Override//work
	public Shift updateShift(Shift shift) {
		
		Optional<Shift> opt=shiftRepository.findById(shift.getShiftId());
		if(opt.isPresent()) {
			Shift sft=opt.get();
			sft.setShiftName(shift.getShiftName());
			sft.setEndTime(shift.getEndTime());
			sft.setStartTime(shift.getStartTime());
			return shiftRepository.save(sft);
		}
		else
			throw new ShiftException("Inviled shift Id .");
		
	}

	@Override//work
	public String removeShift(Integer shiftId) {
		
		Optional<Shift> opt=shiftRepository.findById(shiftId);
		if(opt.isPresent()) {
			 shiftRepository.delete(opt.get());
			 return "Deleted ...";
		}else
			throw new ShiftException("Inviled shiftNo .");
		
	}

	@Override//work
	public Seat addSeat(Integer shiftNo) {

		Optional<Shift> op=shiftRepository.findById(shiftNo);
		if(op.isPresent()) {
			Seat seat=new Seat();
			Shift s=op.get();
			seat.setFloor(s.getFloor().getFloorNo() ); ;
			List<Seat> sl=new ArrayList<>();
			seat.setShift(s);
			sl.add(seat);
			s.setSeatList(sl);
			return seatRepository.save(seat);
		}else {
			throw new ShiftException("Inviled shiftNo .");
		}

	}

	@Override//work  
	public String removeStudentSeat(Integer seatNo_Or_UserId ) {
		
		Optional<Student> op=studentRepository.findById(seatNo_Or_UserId);
		if(op.isEmpty()) {
			Optional<Seat> opt=seatRepository.findById(seatNo_Or_UserId);
			if(opt.isPresent() && opt.get().getStudent()!=null ){
				Seat st=opt.get();
				st.setStudent(null);
				seatRepository.save(st);	
			
			return "Student removed successfully from seat no."+seatNo_Or_UserId;
			}else
				throw new SeatException("Inviled seatNo :- "+seatNo_Or_UserId);
		}else {
				Student st=op.get();
				if(st.getSeats().size()>0 ) {
					List<Seat> seat=st.getSeats();
					for(Seat s:seat) {
						s.setStudent(null);
					}
					seatRepository.saveAll(seat);	
					return "Use Successfully removed  from all seats with User Id :- "+seatNo_Or_UserId;
				}else
					throw new SeatException("Inviled UserId :- "+seatNo_Or_UserId);
		}

	}

	@Override//work
	public Library addLibrary(Library library) {
		List<Floor> fl=library.getFloorList();
		for(Floor i:fl) {
			i.setLibrary(library);
		}
		return libraryRepository.save(library);
	}
		

}
