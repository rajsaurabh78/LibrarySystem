package com.Library.service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import com.Library.modal.Authority;
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
	

	@Override
	public List<Student> getAllStudentInSortingOrder(String field, String direction)  {

		if(direction.toUpperCase().equals("ASC")||direction.toUpperCase().equals("DSC")) {
			List<Student> sList=studentRepository.findAll(
				
				direction.toUpperCase().equals("ASC")? Sort.by(field).ascending() : Sort.by(field).descending());
			if(sList.isEmpty()) {
				throw new StudentException("No any student found .");
			}else 
				return sList;
		}else
			throw new StudentException("Inviled direction .");
		
	}
	
	@Override
	public List<Student> getAllSortedStudentWithPagination(String field, String direction, Integer pageNo,Integer pageSize) {
		
		if(direction.toUpperCase().equals("ASC")||direction.toUpperCase().equals("DSC")) {
			
			PageRequest p = PageRequest.of(pageNo-1, pageSize, direction.toUpperCase().equals("ASC") ? Sort.by(field).ascending() : Sort.by(field).descending());
			Page<Student> page= studentRepository.findAll(p);
			List<Student> students= page.getContent();
			if(students.isEmpty()) {
				throw new StudentException("No any student found .");
			}else 
				return students;
			
		}else
			throw new StudentException("Inviled direction .");	
			
	}

	@Override
	public Student getStudentById(Integer id) {

		return studentRepository.findById(id).orElseThrow(()->new StudentException("Inviled UserId/studentId ."));
	}

	@Override
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

	@Override
	public List<Student> getAllStudentByFloor(Integer floorNo) {

		List<Seat> seats=seatRepository.findAll();
		if(seats.isEmpty()) {
			List<Student> sList =new ArrayList<>();
			for(Seat st:seats) {
				if(st.getFloor()==floorNo ) {
					sList.add(st.getStudent());
				}
			}
			if(sList.size()>0) {
				return sList;
			}else
				throw new StudentException("No student found .");
		}else
			throw new FloorException("Inviled floor no .");
		
	}

	@Override
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

	@Override
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
			throw new StudentException("Inviled shifttNo .");
		
	}

	@Override
	public String removeStudent(Integer userId) {
		
		Optional<Student> opt=studentRepository.findById(userId);
		if(opt.isPresent()&& opt.get().getSeats().size()==0){
			Student stu=opt.get();
			studentRepository.delete(stu);
			return "Successfully deleted .";
		}else
			throw new StudentException("Inviled userId ./ Student register with a seat .");
		
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
				if(stu.getPayment()==true&&stu.getSeats().size()==0 ){
					dto.add(new StudentDTO(stu.getName(), stu.getUserId(),stu.getPayment()));
				}
			}
			if(dto.isEmpty()) {
				throw new StudentException("No record found .");
			}else
			return dto;
		}
	}

	@Override//not_work_proper 
	public String studentSeatAllotement(Integer Id) {
		
		Optional<Student> opt=studentRepository.findById(Id);
		if(opt.isPresent()&& opt.get().getPayment()==true){
			boolean flag=true;
			Student stu=opt.get();
			String shiftName=stu.getWantedShift();
			Seat seat;
			if(shiftName.equals("FIRST") ) {
				seat=seatRepository.getFirstShiftSeat();
			}else if(shiftName.equals("SECOND")) {
				seat=seatRepository.getSecondSeat();
			}else if(shiftName.equals("THIRD")) {
				seat=seatRepository.getThirdSeat();
			}else {
				throw new ShiftException("Inviled shift name .");
			}
			List<Seat> seats=stu.getSeats();			
			if(seats.size()>0) {
				for(Seat s:seats) {
					if(s.getShift().getShiftName().toUpperCase().equals(shiftName)) {
						flag=false;
						break;
					}	
				}
			}
			if(seat!=null) {
				if(flag) {
					List<Seat> sl=new ArrayList<>();
					seat.setStudent(stu);
					sl.add(seat);
					stu.setSeats(sl);
					stu.setProvidedShift(stu.getProvidedShift()+shiftName+",");
					studentRepository.save(stu);			
					return "Registered in shift."+seat.getShift().getShiftName() ;
				}else
					throw new SeatException("Student is already in shift "+seat.getShift().getShiftName() );
			}else {
				throw new SeatException("Seat not avalible .");
			}
		}else
			throw new StudentException("Inviled Student Id / Payment is not clear.");
		
	}
	
	
	@Override
	public String seatAllotementManual(Integer id,String shiftName) {
		Seat seat;
		if(shiftName.equals("FIRST") ) {
			seat=seatRepository.getFirstShiftSeat();
		}else if(shiftName.equals("SECOND")) {
			seat=seatRepository.getSecondSeat();
		}else if(shiftName.equals("THIRD")) {
			seat=seatRepository.getThirdSeat();
		}else {
			throw new ShiftException("Inviled shift name .");
		}
		Optional<Student> opt=studentRepository.findById(id);
		if(opt.isPresent()&& opt.get().getPayment()==true){
			boolean flag=true;
			Student stu=opt.get();
			List<Seat> seats=stu.getSeats();			
			if(seats.size()>0) {
				for(Seat s:seats) {
					if(s.getShift().getShiftName().toUpperCase().equals(shiftName)) {
						flag=false;
						break;
					}	
				}
			}
			if(seat!=null) {
				if(flag) {
					List<Seat> sl=new ArrayList<>();
					seat.setStudent(stu);
					sl.add(seat);
					stu.setProvidedShift(stu.getProvidedShift()+shiftName+",");
					stu.setSeats(sl);
					studentRepository.save(stu);			
					return "Registered in shift."+seat.getShift().getShiftName() ;
				}else
					throw new SeatException("Student is already in shift "+seat.getShift().getShiftName() );
			}else {
				throw new SeatException("Seat not avalible .");
			}
			
		}else
			throw new StudentException("Inviled Student Id / Payment is not clear.");
		
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
			ad.setDOB(admin.getDOB());			
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
	public Floor addFloor(Floor floor,Integer libraryId) {
		Optional<Library> labs=libraryRepository.findById(libraryId);
		if(labs.isPresent()) {
			List<Floor> flrs=new ArrayList<>();
			floor.setLibrary(labs.get());
			
			List<Shift> shifts=floor.getShiftList();
			for(Shift s:shifts) {
				s.setFloor(floor);
			}
			flrs.add(floor);
			labs.get().setFloorList(flrs);
			return floorRepository.save(floor);
		}else {
			throw new LibraryException("Inviled library Id .");
		}
		
		
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

	@Override
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

	@Override
	public String removeShift(Integer shiftId) {
		
		Optional<Shift> opt=shiftRepository.findById(shiftId);
		if(opt.isPresent()) {
			 shiftRepository.delete(opt.get());
			 return "Deleted ...";
		}else
			throw new ShiftException("Inviled shiftNo .");
		
	}

	@Override
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

	@Override
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

	@Override
	public Library addLibrary(Library library) {
		List<Floor> fl=library.getFloorList();
		for(Floor i:fl) {
			i.setLibrary(library);
		}
		return libraryRepository.save(library);
	}

	@Override
	public Admin addAdmin(Admin admin) {

		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		List<Authority> auths= new ArrayList<>();
		auths.add(new Authority(null,"ROLE_ADMIN",null,admin));
		auths.add(new Authority(null,"ROLE_USER",null,admin));
		admin.setAuthorities(auths);
		return adminRepository.save(admin);
		
	}

	@Override
	public List<Admin> allAdmin(Integer pageNo,Integer pageSize) {
			
			PageRequest p = PageRequest.of(pageNo-1, pageSize);
			Page<Admin> page= adminRepository.findAll(p);
			List<Admin> students= page.getContent();
			if(students.isEmpty()) {
				throw new StudentException("No any student found .");
			}else 
				return students;
			
	}

	@Override
	public Admin getAdminById(Integer id) {

		return adminRepository.findById(id).orElseThrow(()->new AdminException("Inviled Admin Id ."));
		
	}




		

}
