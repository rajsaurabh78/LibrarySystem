package com.Library.modal;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer seatNo;
	
	private Integer floor;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="shiftId")
	private Shift shift;
	
//	@OneToOne( mappedBy = "seat", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @PrimaryKeyJoinColumn
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="studentId")
	private Student student;



}
