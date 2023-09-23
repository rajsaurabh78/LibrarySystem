package com.Library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Library.exception.ShiftException;
import com.Library.exception.StudentException;
import com.Library.modal.Floor;
import com.Library.modal.Library;
import com.Library.modal.Shift;
import com.Library.modal.Student;
import com.Library.repository.FloorRepository;
import com.Library.repository.studentRepository;
@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private studentRepository studentRepository;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private FloorRepository floorRepository;

	@Override
	public Student registerStudent(Student student) {
		
	//	student.setPassword(passwordEncoder.encode(student.getPassword()));
		return studentRepository.save(student);
	}

	@Override
	public List<Shift> getAllShiftByFloorNo(Integer floorNo) {
		
		Optional<Floor> opt=floorRepository.findById(floorNo);
		if(opt.isPresent()) {
			return opt.get().getShiftList();
		}
		else
			throw new ShiftException("Inviled shift Id .");

	}

	@Override
	public Library getAllDetails() {
		
		return null;
	}

	@Override
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
	//		st.setPassword(passwordEncoder.encode(student.getPassword()));
			return studentRepository.save(st);
		}
	}

	@Override
	public Student getOwnProfile() {
		// TODO Auto-generated method stub
		return null;
	}

}
