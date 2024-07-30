package com.Library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.Library.modal.Student;
@Repository
public interface studentRepository extends JpaRepository<Student, Integer>,PagingAndSortingRepository<Student, Integer>{
	Optional<Student> findByEmail(String email);
	List<Student> findByAddressContaining(String address);
	List<Student> findByProvidedShift(String shift);
	
}
