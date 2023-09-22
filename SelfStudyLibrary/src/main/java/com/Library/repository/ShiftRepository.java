package com.Library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Library.modal.Admin;
import com.Library.modal.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Integer>{
//	Optional<Admin> findByMobile(String mobile);
}
