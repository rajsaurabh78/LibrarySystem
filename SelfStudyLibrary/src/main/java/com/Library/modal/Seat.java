package com.Library.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="seat_seq")
	@SequenceGenerator(name="seat_seq", sequenceName="seat_seq",allocationSize=1, initialValue=1)
	private Integer seatNo;
	
	private Integer floor;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="shiftId")
	private Shift shift;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="studentId")
	private Student student;



}
