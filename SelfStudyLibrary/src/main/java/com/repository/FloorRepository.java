package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modal.Admin;
import com.modal.Floor;

public interface FloorRepository extends JpaRepository<Floor, Integer>{
	Optional<Admin> findByMobile(String mobile);
}
