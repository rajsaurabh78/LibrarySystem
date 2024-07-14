package com.Library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Library.exception.LibraryException;
import com.Library.exception.ShiftException;
import com.Library.modal.Authority;
import com.Library.modal.Floor;
import com.Library.modal.Library;
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
		student.setWantedShift(student.getWantedShift().toUpperCase() );
		List<Authority> auths= new ArrayList<>();
		auths.add(new Authority(null,"ROLE_STUDENT", student,null));
		auths.add(new Authority(null,"ROLE_USER", student,null));
		student.setAuthority(auths);
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
//		for(Library l:labs) {
//			List<Floor> fls=l.getFloorList();
//			for(Floor f:fls) {
//				List<Shift> sft=f.getShiftList();
//				for(Shift s:sft) {
//					s.setSeatList(null);
//				}
//			}
//			
//		}
		if(labs.size()>0) {
			return labs;
		}else
			throw new LibraryException("No details avalible...");
		
	}

	@Override//work
	public Student updateStudent(Student st) throws LoginException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Student student = studentRepository.findByEmail(authentication.getName())
				.orElseThrow(() -> new LoginException("Please Login First"));
		
		
		System.out.println(student.getEmail());
			if(st.getAddress()!=null) {
				student.setAddress(st.getAddress());
			}
			if(st.getEmail()!=null) {
				student.setEmail(st.getEmail());
			}
			if(st.getMobile()!=null) {
				student.setMobile(st.getMobile());
			}
			if(st.getName()!=null) {
				student.setName(st.getName());
			}
			if(st.getDOB()!=null) {
				student.setDOB(st.getDOB());
			}
			if(st.getWantedShift()!=null) {
				student.setWantedShift(st.getWantedShift().toUpperCase());
			}
			if(st.getPassword()!=null) {
				student.setPassword(passwordEncoder.encode(student.getPassword()));
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

//	@Override
//	public Admin getAdminByMobile(String mobile) {
//
//		return adminRepository.findByMobile(mobile).orElseThrow(()->new AdminException("Inviled mobile no ."));
//		
//	}

}
