package com.Library.modal;

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
import jakarta.persistence.SequenceGenerator;
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
public class Floor {
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="floor_seq")
	@SequenceGenerator(name="floor_seq", sequenceName="floor_seq",allocationSize=100, initialValue=100)
	private Integer floorNo;
	
	@Column(unique = true)
	@NotBlank(message ="Name should not Blank." )
	@NotEmpty(message ="Name should not Empty." )
	@NotNull(message ="Name should not Null." )
	private String name;
	
	@ManyToOne
	@JoinColumn(name="libraryId")
	@JsonIgnore
	private Library library;
	
	@OneToMany(mappedBy = "floor",cascade = CascadeType.ALL)
	private List<Shift> shiftList=new ArrayList<>();
}
