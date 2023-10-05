package com.Library.modal;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends Details{
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="student_seq")
	@SequenceGenerator(name="student_seq", sequenceName="student_seq",allocationSize=1, initialValue=1001)
	private Integer userId;
	
	@NotNull(message ="Payment type should not Null." )
	private Boolean payment;
	
	@NotBlank(message ="WantedShift should not Blank." )
	@NotEmpty(message ="WantedShift should not Empty." )
	@NotNull(message ="WantedShift should not Null." )
	private String wantedShift;
	
	private String providedShift;
	
	@OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
	private List<Seat> seats=new ArrayList<>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Authority> authority=new ArrayList<>();

}
