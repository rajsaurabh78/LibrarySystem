package com.Library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.Library.modal.Admin;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>,PagingAndSortingRepository<Admin, Integer>{
	Optional<Admin> findByMobile(String mobile);
}
