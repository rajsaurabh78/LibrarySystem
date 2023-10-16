package com.Library.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Library.exception.LibraryException;
import com.Library.exception.ShiftException;
import com.Library.exception.StudentException;
import com.Library.modal.Authority;
import com.Library.modal.Floor;
import com.Library.modal.Library;
import com.Library.modal.Shift;
import com.Library.modal.Student;
import com.Library.repository.AdminRepository;
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
	public Student updateStudent(Student student) {
		
		Optional<Student> opt=studentRepository.findById(student.getUserId());
		if(opt.isEmpty()) {
			throw new StudentException("Inviled User id .");
		}else {
			Student st=opt.get();
			st.setAddress(student.getAddress());
			st.setEmail(student.getEmail());
			st.setName(student.getName());
			st.setDOB(student.getDOB());
			st.setWantedShift(student.getWantedShift().toUpperCase());
			st.setPassword(passwordEncoder.encode(student.getPassword()));
			return studentRepository.save(st);
		}
	}

	@Override
	public Student getOwnProfile() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public Admin getAdminByMobile(String mobile) {
//
//		return adminRepository.findByMobile(mobile).orElseThrow(()->new AdminException("Inviled mobile no ."));
//		
//	}

}
