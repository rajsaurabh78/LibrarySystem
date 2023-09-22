package com.Library.modal;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Floor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer floorNo;
	private String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="libId")
	private Library library;
	
	@OneToMany(mappedBy = "floor",cascade = CascadeType.ALL)
	private List<Shift> shiftList=new ArrayList<>();
}
