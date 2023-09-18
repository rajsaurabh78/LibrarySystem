package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.modal.Student;
@Repository
public interface studentRepository extends JpaRepository<Student, Integer>{
	Optional<Student> findByEmail(String email);
	Optional<Student> findBySeatNo(Integer seatNo);
}
