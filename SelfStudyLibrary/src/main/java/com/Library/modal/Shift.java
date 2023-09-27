package com.Library.modal;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	
	private String shiftName;
	
	private LocalTime startTime;
	
	private LocalTime endTime;
	
//	@OneToMany(cascade =  CascadeType.ALL)
//	private List<Student> studentList=new ArrayList<>();
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="floorId")
	private Floor floor;
	
	@OneToMany(mappedBy = "shift",cascade =  CascadeType.ALL,orphanRemoval = false)
	private List<Seat> seatList=new ArrayList<>();
	
}
