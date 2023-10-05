package com.Library.modal;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Library {
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="library_seq")
	@SequenceGenerator(name="library_seq", sequenceName="library_seq",allocationSize=1, initialValue=1)
	private Integer labId;
	
	@Column(unique = true)
	@NotBlank(message ="Name should not Blank." )
	@NotEmpty(message ="Name should not Empty." )
	@NotNull(message ="Name should not Null." )
	private String name;
	
	@NotBlank(message ="Address should not Blank." )
	@NotEmpty(message ="Address should not Empty." )
	@NotNull(message ="Address should not Null." )
	private String address;
	
	@OneToMany(mappedBy = "library",cascade = CascadeType.ALL)
	private List<Floor> floorList=new ArrayList<>();
}
