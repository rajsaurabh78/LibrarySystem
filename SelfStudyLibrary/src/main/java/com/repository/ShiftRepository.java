package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modal.Admin;
import com.modal.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Integer>{
	Optional<Admin> findByMobile(String mobile);
}
