package com.Library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Library.modal.Admin;
import com.Library.modal.Details;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	Optional<Details> findByMobile(String mobile);
}
