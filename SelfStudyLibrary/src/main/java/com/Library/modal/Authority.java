package com.Library.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Authority {
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="authority_seq")
	@SequenceGenerator(name="authority_seq", sequenceName="authority_seq",allocationSize=1, initialValue=500)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@ManyToOne
	@JsonIgnore
	private Student user;
	
	@ManyToOne
	@JsonIgnore
	private Admin admin;
	
}
