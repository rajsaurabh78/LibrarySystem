package com.Library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Library.DTO.StudentDTO;
import com.Library.exception.AdminException;
import com.Library.exception.FloorException;
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
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	

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
		//		seats+=i.getTotalSeats();
			}
		}
		if(seats==0) {
			throw new SeatException("Seat not available");
		}
		else
			return seats;
	}

	@Override
	public List<Student> getAllStudentShiftWise(Integer shiftNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeStudent(Integer seatsNo) {
//
//		Optional<Student> opt=studentRepository.findBySeat(seatsNo);
//		if(opt.isPresent()) {
//			studentRepository.delete(opt.get());
//			return "Successfully deleted .";
//		}else
//			throw new StudentException("Inviled seatNo .");
		return null;
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
			
			Library lib=(Library) libraryRepository.findAll();
			List<Floor> floor=lib.getFloorList();
			for(Floor i:floor) {
				List<Shift> shift=i.getShiftList();
				for(Shift s:shift) {
					if(s.getShiftId()==stu.getShift()) {
						if(s.getAvalibleSeats()>0) {
							Seat seat=stu.getSeat();
							seat.setStudent(stu);
							stu.setSeat(seat);
							s.setAvalibleSeats(s.getAvalibleSeats()-1);
							seatRepository.save(seat);
							return "Updated "+stu.toString();
						}else {
							throw new SeatException("Seat not avalible");
						}
					}else {
						throw new ShiftException("No seat avalible in shift :- "+stu.getShift());
					}
				}
			}	throw new StudentException("Inviled Student Id .");
		}else
			throw new StudentException("Inviled Student Id .");
		
	}

	@Override
	public Admin registerAdmin(Admin admin) {
	//	admin.setPassword(passwordEncoder.encode(admin.getPassword()));
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
		//	ad.setPassword(passwordEncoder.encode(admin.getPassword()));
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
	public Shift addShift(Shift shift,Integer floorNo) {
		List<Seat> seats=seatRepository.findAll();
		int size=0;
		for(Seat s:seats) {
			if(s.getFloor()==floorNo) {
				size+=seats.size();		
			}
		}
		shift.setAvalibleSeats(size+shift.getSeatList().size());	
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
		//	sft.setTotalSeats(shift.getTotalSeats());
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

//	@Override
//	public String updateSeats(Integer floorNo, Integer newSeats) {
//
//		Optional<Floor> opt=floorRepository.findById(floorNo);
//		if(opt.isPresent()) {
//			Floor fl=opt.get();
//			List<Shift> list=fl.getShiftList();
//			for(Shift i:list) {
//				i.setAvalibleSeats(i.getAvalibleSeats()+newSeats);			
//			}
//			return "Seats increased by :- "+newSeats;
//		}
//		else
//			throw new FloorException("Inviled floor Id .");
//		
//	}

	@Override
	public Seat addSeat(Seat seat) {
		seatRepository.save(seat);
		List<Floor> fl=floorRepository.findAll();
		for(Floor f:fl) {
			List<Shift> st=f.getShiftList();
			for(Shift i:st) {
				i.setAvalibleSeats(i.getAvalibleSeats()+1);
			}
		}
		return seat;
		
	}

	@Override
	public String removeStudentSeat(Integer seatNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Library addLibrary(Library library) {
		List<Floor> fl=library.getFloorList();
		for(Floor i:fl) {
			i.setLibrary(library);
		}
		return libraryRepository.save(library);
	}
		

}
