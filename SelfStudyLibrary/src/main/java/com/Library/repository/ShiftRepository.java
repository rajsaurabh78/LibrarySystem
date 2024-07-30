package com.Library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Library.modal.Shift;
import com.Library.modal.Student;

public interface ShiftRepository extends JpaRepository<Shift, Integer>{
	List<Student> findByShiftName(String address);
}
