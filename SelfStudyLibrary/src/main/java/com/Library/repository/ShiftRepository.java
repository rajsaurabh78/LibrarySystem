package com.Library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Library.modal.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Integer>{
	
}
