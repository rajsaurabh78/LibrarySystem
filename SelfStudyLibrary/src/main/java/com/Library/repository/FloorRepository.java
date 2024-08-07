package com.Library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.Library.modal.Floor;

public interface FloorRepository extends JpaRepository<Floor, Integer>,PagingAndSortingRepository<Floor, Integer>{
	//Optional<Admin> findByMobile(String mobile);
}
