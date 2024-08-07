package com.Library.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Library.DTO.UpdateDetailsDTO;
import com.Library.exception.LibraryException;
import com.Library.exception.ShiftException;
import com.Library.exception.StudentException;
import com.Library.modal.Authority;
import com.Library.modal.Floor;
import com.Library.modal.Library;
import com.Library.modal.Role;
import com.Library.modal.Seat;
import com.Library.modal.Shift;
import com.Library.modal.Student;
import com.Library.repository.FloorRepository;
import com.Library.repository.LibraryRepository;
import com.Library.repository.studentRepository;
@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private studentRepository studentRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private FloorRepository floorRepository;
	
	@Autowired
	private LibraryRepository libraryRepository;
	

	@Override//work
	public Student registerStudent(Student student) {
		
		student.setPassword(passwordEncoder.encode(student.getPassword()));
		student.setProvidedShift(null);
		List<Authority> auths= new ArrayList<>();
		auths.add(new Authority(null,Role.ROLE_STUDENT, student,null));
		auths.add(new Authority(null,Role.ROLE_USER, student,null));
		student.setAuthority(auths);
		student.setPayment(false);
		return studentRepository.save(student);
	}

	@Override//work
	public List<Shift> getAllShiftByFloorNo(Integer floorNo) {
		
		Optional<Floor> opt=floorRepository.findById(floorNo);
		if(opt.isPresent()) {
			Floor fl=opt.get();
			List<Shift> sfts=fl.getShiftList();
			for(Shift s:sfts) {
				s.setSeatList(null);
			}
			return fl.getShiftList();
		}
		else
			throw new ShiftException("Inviled floor No .");

	}

	@Override//work
	public List<Library> getAllDetails() {
		List<Library> labs=libraryRepository.findAll();
		for(Library l:labs) {
			List<Floor> fls=l.getFloorList();
			for(Floor f:fls) {
				List<Shift> sft=f.getShiftList();
				for(Shift seat:sft) {
					List<Seat>seats= seat.getSeatList().stream()
							.filter(s->s.getStudent()==null).collect(Collectors.toList());
					seat.setSeatList(seats);
				}
			}
			
		}
		if(labs.size()>0) {
			return labs;
		}else
			throw new LibraryException("No details avalible...");
		
	}

	@Override//work
	public Student updateStudent(UpdateDetailsDTO updateDetailsDTO) throws LoginException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Student student = studentRepository.findByEmail(authentication.getName())
				.orElseThrow(() -> new LoginException("Please Login First"));
		
			if(updateDetailsDTO.getAddress()!=null) {
				student.setAddress(updateDetailsDTO.getAddress());
			}
			if(updateDetailsDTO.getEmail()!=null) {
				student.setEmail(updateDetailsDTO.getEmail());
			}
			if(updateDetailsDTO.getMobile()!=null) {
				student.setMobile(updateDetailsDTO.getMobile());
			}
			if(updateDetailsDTO.getName()!=null) {
				student.setName(updateDetailsDTO.getName());
			}
			if(updateDetailsDTO.getDOB()!=null) {
				student.setDOB(updateDetailsDTO.getDOB());
			}
			if(updateDetailsDTO.getWantedShift()!=null) {
				student.setWantedShift(updateDetailsDTO.getWantedShift());
			}
			if(updateDetailsDTO.getPassword()!=null) {
				student.setPassword(passwordEncoder.encode(updateDetailsDTO.getPassword()));
			}
			return studentRepository.save(student);
	}

	@Override
	public Student getOwnProfile() throws LoginException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Student st = studentRepository.findByEmail(authentication.getName())
				.orElseThrow(() -> new LoginException("Please Login First"));
		System.out.println(st.getEmail());
		return st;
	}

	@Override
	public String forgetPassword(String email, LocalDate dob, String password) {
		Student student=studentRepository.findByEmail(email)
				.orElseThrow(()->new StudentException("Inviled email."));
		if(student.getDOB().equals(dob)) {
			student.setPassword(passwordEncoder.encode(password));
			studentRepository.save(student);
			return "Password updated.";
		}else {
			throw new StudentException("Date of birth did not match.");
		}
	}


}
