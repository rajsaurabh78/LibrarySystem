package com.Library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Library.modal.Authority;
import com.Library.modal.Student;
import com.Library.repository.studentRepository;
@Service
public class UserUserDetailsService implements UserDetailsService{

	@Autowired
	private studentRepository studentRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Student> opt= studentRepository.findByEmail(username);

		if(opt.isPresent()) {
			
			Student stu= opt.get();
			
			List<GrantedAuthority> authorities = new ArrayList<>();
		
			
			
			List<Authority> auths= stu.getAuthority();
			
			for(Authority auth:auths) {
				SimpleGrantedAuthority sga=new SimpleGrantedAuthority(auth.getName());
				System.out.println("siga "+sga);
				authorities.add(sga);
			}
			
			System.out.println("granted authorities "+authorities);
			
			
			return new User(stu.getEmail(), stu.getPassword(), authorities);
			
			
		}else
			throw new BadCredentialsException("User Details not found with this username: "+username);
	}
	
	


}
