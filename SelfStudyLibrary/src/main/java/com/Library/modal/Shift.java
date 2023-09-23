package com.Library.modal;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Shift {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer shiftId;
	
	@Column(unique = true)
	private String shiftName;
	
	private Integer avalibleSeats;
	
	private LocalTime startTime;
	
	private LocalTime endTime;
	
	@OneToMany(cascade =  CascadeType.ALL)
	private List<Student> studentList=new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@NotBlank
	@NotEmpty
	@NotNull
	private Floor floor;
	
	@OneToMany(cascade =  CascadeType.ALL)
	private List<Seat> seatList=new ArrayList<>();
	
}
