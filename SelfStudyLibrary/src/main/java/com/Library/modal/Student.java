package com.Library.modal;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	private String address;
	
	@Column(unique = true)
	private String mobile;
	
	//private Integer seatNo;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate DOB;
	
	private Boolean payment;
	
	private Integer shift;
	
    @OneToOne( mappedBy = "student", cascade = CascadeType.ALL)
    @JoinColumn(name="seatNo")
	private Seat seat;
}
