package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modal.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	Optional<Admin> findByMobile(String mobile);
}
