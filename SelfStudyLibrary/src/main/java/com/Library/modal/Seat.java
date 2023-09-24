package com.Library.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Shift shift;
	
	@OneToOne
    @PrimaryKeyJoinColumn
	private Student student;
}
