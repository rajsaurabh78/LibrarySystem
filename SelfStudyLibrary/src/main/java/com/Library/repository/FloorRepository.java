package com.Library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Library.modal.Details;
import com.Library.modal.Floor;

public interface FloorRepository extends JpaRepository<Floor, Integer>{
	//Optional<Admin> findByMobile(String mobile);
}
