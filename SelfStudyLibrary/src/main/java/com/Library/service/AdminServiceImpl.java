package com.Library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Library.DTO.SeatDTO;
import com.Library.DTO.ShiftDTO;
import com.Library.DTO.StudentDTO;
import com.Library.DTO.UpdateDetailsDTO;
import com.Library.DTO.UpdateLibraryDTO;
import com.Library.DTO.UpdateShiftDTO;
import com.Library.exception.AdminException;
import com.Library.exception.FloorException;
import com.Library.exception.LibraryException;
import com.Library.exception.LoginException;
import com.Library.exception.SeatException;
import com.Library.exception.ShiftException;
import com.Library.exception.StudentException;
import com.Library.modal.Admin;
import com.Library.modal.Authority;
import com.Library.modal.Floor;
import com.Library.modal.Library;
import com.Library.modal.Role;
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
		if(direction.toUpperCase().equalsIgnoreCase("ASC")||direction.toUpperCase().equalsIgnoreCase("DSC")) {
			List<Student> sList=studentRepository.findAll(direction.toUpperCase().equals("ASC")? Sort.by(field).ascending() : Sort.by(field).descending());
			if(sList.isEmpty()) {
				throw new StudentException("No any student found .");
			}else 
				return sList;
		}else {
			throw new StudentException("Invalid direction. Use 'ASC' or 'DSC'.");
		}
		
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

		List<Seat> seats=seatRepository.allSeatsByFloor(floorNo);
		if(!seats.isEmpty()) {
			List<Student> sList =new ArrayList<>();
			for(Seat st:seats) {
				sList.add(st.getStudent());
			}
		//	if(sList.size()>0) {
				return sList;
		//	}else
			//	throw new StudentException("No student found .");
		}else
			throw new FloorException("Inviled floor no ./ No data found.");
		
	}

	@Override
	public List<SeatDTO> getAllAvalibleSeats() {

		List<Seat> seatList=seatRepository.findAll().stream().filter(s->s.getStudent()==null).toList();
		if(seatList.isEmpty()) {
			throw new SeatException("Seat not available");
		}
		List<SeatDTO> sdto=new ArrayList<>();
		for(Seat s:seatList) {
			SeatDTO seat=new SeatDTO(s.getSeatNo(),s.getShift().getShiftName().toString(),s.getShift().getFloor().getName());
			sdto.add(seat);
		}
		return sdto;
	}

	@Override
	public List<Student> getAllStudentShiftWise(String shift) {
		
		List<Student> students= seatRepository.findAll().stream()
				.filter(s->s.getShift().getShiftName().toString().equals(shift.toUpperCase())&&s.getStudent()!=null).collect(Collectors.toList())
				.stream().map(s->s.getStudent()).collect(Collectors.toList());
		if(students.size()==0 ) {
			throw new StudentException("No data present .");
		}else
			return students;
		
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

		List<Student> sList=studentRepository.findByAddressContaining(address);
		if(!sList.isEmpty()) {
			return sList;
		}
		else
			throw new StudentException("No record found .");
	}

	@Override
	public List<StudentDTO> getAllStudentWithNoSeatNo() {

		List<Student> sList=studentRepository.findAll().stream().filter(s->s.getSeats().size()==0 && s.getPayment()==true).toList();
		if(sList.isEmpty()) {
			throw new StudentException("No record found .");
		}
		List<StudentDTO> dto=new ArrayList<>();
		for(Student stu:sList) {
				dto.add(new StudentDTO(stu.getName(), stu.getUserId(),stu.getPayment(),stu.getWantedShift().toString(),stu.getProvidedShift()));		
		}
		return dto;
	}

	@Override//not_work_proper 
	public String studentSeatAllotement(Integer Id) {
		
		Optional<Student> opt=studentRepository.findById(Id);
		if(opt.isPresent()&& opt.get().getPayment()==true){
			boolean flag=true;
			Student stu=opt.get();
			String shiftName=stu.getWantedShift().toString();
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
					if(s.getShift().getShiftName().toString().equals(shiftName)) {
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
					stu.setProvidedShift(shiftName+",");
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
					if(s.getShift().getShiftName().toString().equals(shiftName)) {
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
					if(stu.getProvidedShift()==null ) {
						stu.setProvidedShift(shiftName+",");
					}else {
						stu.setProvidedShift(stu.getProvidedShift()+shiftName+",");
					}
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
	public Admin updateAdmin(UpdateDetailsDTO updateDetailsDTO) throws LoginException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Admin admin = adminRepository.findByMobile(authentication.getName())
				.orElseThrow(() -> new LoginException("Please Login First"));

		if(updateDetailsDTO.getAddress()!=null) {
			admin.setAddress(updateDetailsDTO.getAddress());
		}
		if(updateDetailsDTO.getEmail()!=null) {
			admin.setEmail(updateDetailsDTO.getEmail());
		}
		if(updateDetailsDTO.getMobile()!=null) {
			admin.setMobile(updateDetailsDTO.getMobile());
		}
		if(updateDetailsDTO.getName()!=null) {
			admin.setName(updateDetailsDTO.getName());
		}
		if(updateDetailsDTO.getDOB()!=null) {
			admin.setDOB(updateDetailsDTO.getDOB());
		}
		if(updateDetailsDTO.getPassword()!=null) {
			admin.setPassword(passwordEncoder.encode(updateDetailsDTO.getPassword()));
		}		
		
		return adminRepository.save(admin);
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
		Library labs=libraryRepository.findById(libraryId)
				.orElseThrow(()->new LibraryException("Inviled library Id ."));

		List<Floor> flrs=new ArrayList<>();
		floor.setLibrary(labs);
		List<Shift> shifts=floor.getShiftList();
		for(Shift s:shifts) {
			s.setFloor(floor);
		}
		flrs.add(floor);
		labs.setFloorList(flrs);
		return floorRepository.save(floor);	
		
	}

	@Override
	public Floor updateFloorName(Integer floorNo,String newName) {
		
		Floor floor=floorRepository.findById(floorNo)
				.orElseThrow(()-> new FloorException("Inviled floorNo ."));
		if(newName!=null) {
			floor.setName(newName);
		}
		
		return floorRepository.save(floor);
		
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
	public Shift addShift(ShiftDTO shiftDto,Integer floorNo) {	
		Optional<Floor> opt=floorRepository.findById(floorNo);
		Shift shift =new Shift();
		shift.setShiftName(shiftDto.getShiftName());
		shift.setStartTime(shiftDto.getStartTime());
		shift.setEndTime(shiftDto.getEndTime());
		List<Shift> sl=new ArrayList<>();
		sl.add(shift);
		if(opt.isPresent()) {
			Floor fl=opt.get();
			fl.setShiftList(sl);
			shift.setFloor(fl);
			List<Seat> sList=new ArrayList<>();
			for(int i=0;i<shiftDto.getNoOfSeats();i++) {
				Seat s=new Seat();
				s.setFloor(floorNo);
				s.setShift(shift);
				sList.add(s);
			}
			shift.setSeatList(sList);
			return shiftRepository.save(shift);
		}else
			throw new FloorException("Inviled floorNo .");
	}

	@Override
	public Shift updateShift(UpdateShiftDTO updateShiftDTO) {
		
		Shift shift=shiftRepository.findById(updateShiftDTO.getShiftId())
				.orElseThrow(()-> new ShiftException("Inviled shift Id ."));
		
		if(updateShiftDTO.getShiftName()!=null) {
			shift.setShiftName(updateShiftDTO.getShiftName());
		}
		if(updateShiftDTO.getStartTime()!=null) {
			shift.setStartTime(updateShiftDTO.getStartTime());
		}
		if(updateShiftDTO.getEndTime()!=null) {
			shift.setEndTime(updateShiftDTO.getEndTime());
		}
		
		return shiftRepository.save(shift);	
		
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
	public List<Seat> addSeat(Integer shiftNo,Integer noOfSeats) {

		Shift shift=shiftRepository.findById(shiftNo)
				.orElseThrow(()->new ShiftException("Inviled shiftNo ."));
			
			List<Seat> sl=new ArrayList<>();
			for(int i=0;i<noOfSeats;i++) {
				Seat seat=new Seat();
				seat.setFloor(shift.getFloor().getFloorNo() );
				seat.setShift(shift);
				sl.add(seat);
			}
			
			shift.setSeatList(sl);
			shiftRepository.save(shift);
			return sl;

	}

	@Override
	public String removeStudentSeat(Integer seatNo_Or_UserId ) {
		
		Optional<Student> op=studentRepository.findById(seatNo_Or_UserId);
		if(op.isEmpty()) {
			Seat seat=seatRepository.findById(seatNo_Or_UserId)
					.orElseThrow(()->new SeatException("Inviled seatNo :- "+seatNo_Or_UserId));
			seat.setStudent(null);
			seatRepository.save(seat);
			return "Student removed successfully from seat no."+seatNo_Or_UserId;
		}else {
				Student st=op.get();
				if(st.getSeats().size()>0) {
					List<Seat> seat=st.getSeats();
					for(Seat s:seat) {
						s.setStudent(null);
					}
					st.setProvidedShift(null);
					seatRepository.saveAll(seat);	
					return "User Successfully removed  from all seats with User Id :- "+seatNo_Or_UserId;
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
		auths.add(new Authority(null,Role.ROLE_ADMIN,null,admin));
		auths.add(new Authority(null,Role.ROLE_USER,null,admin));
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

	@Override
	public List<Library> allLibrary() {

		List<Library> libs=libraryRepository.findAll();
		if(libs.size()>0) {
			return libs;
		}else
			throw new LibraryException("No data found.");
	}

	@Override
	public String updateLibrary(Integer lId,UpdateLibraryDTO updateLibraryDTO) {
		
		Library lib=libraryRepository.findById(lId)
				.orElseThrow(()->new LibraryException("Inviled library id"));
		if(updateLibraryDTO.getName()!=null) {
			lib.setName(updateLibraryDTO.getName());
		}
		if(updateLibraryDTO.getAddress()!=null) {
			lib.setAddress(updateLibraryDTO.getAddress());
		}
			
		libraryRepository.save(lib);
		return "Updated";
		
	}

	@Override
	public String deleteLibrary(Integer lId) {
		
		Optional<Library> opt=libraryRepository.findById(lId);
		if(opt.isPresent()) {
			libraryRepository.delete(opt.get());
			return "Deleted";
		}else
			throw new LibraryException("Inviled library id");	
	}

	@Override
	public List<Floor> getFloorbyLid(Integer lId) {
		
		Optional<Library> opt=libraryRepository.findById(lId);
		if(opt.isPresent()) {
			return opt.get().getFloorList();
		}else
			throw new LibraryException("Inviled library id");
		
	}

	@Override
	public List<Shift> getShiftsByFloor(Integer fId) {
		
		Optional<Floor> opt=floorRepository.findById(fId);
		if(opt.isPresent()) {
			return opt.get().getShiftList();
		}else
			throw new FloorException("Inviled Floor id");
		
		
	}

	@Override
	public List<Seat> getSeatsByShift(Integer shiftId) {

		Optional<Shift> opt=shiftRepository.findById(shiftId);
		if(opt.isPresent()) {
			return opt.get().getSeatList();
		}else
			throw new ShiftException("Inviled Shift id");
		
	}

	@Override
	public String deleteSeatById(Integer seatNo) {

		Optional<Seat> opt=seatRepository.findById(seatNo);
		if(opt.isPresent()) {
			Student st=opt.get().getStudent();
			if(st==null) {
				seatRepository.delete(opt.get());
				return "Deleted.";
			}else throw new SeatException("Seat is not empty."); 
		}else
			throw new SeatException("Inviled Seat no. .");
	}

	@Override
	public List<Student> allStudentWithNoPayment() {
		
		List<Student> sList=studentRepository.findAll().stream().filter(s->s.getPayment()==false).toList();
		if(!sList.isEmpty()) {
			return sList;
		}else
			throw new StudentException("Empty List");
		
	}

	@Override
	public String updatePayment(Integer id) {
		
		Optional<Student> opt=studentRepository.findById(id);
		if(opt.isPresent()) {
			opt.get().setPayment(true);	
			studentRepository.save(opt.get());
			return "Payment updated .";
		}else
			throw new StudentException("Inviled userId .");
		
	}

	@Override
	public Admin profile() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Admin admin = adminRepository.findByMobile(authentication.getName())
				.orElseThrow(() -> new com.Library.exception.LoginException("Please Login First"));
		return admin;
	}

}
