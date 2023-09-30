package com.Library.modal;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;

	private Boolean payment;
	
	private String shift;
	
	private Integer floor;
	
	@OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
	private List<Seat> seats=new ArrayList<>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Authority> authority=new ArrayList<>();
	
//    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name="seatNo")
//    @JsonIgnore
//	private Seat seat;
}
