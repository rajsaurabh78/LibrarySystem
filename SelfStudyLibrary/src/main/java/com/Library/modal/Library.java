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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Library {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer labId;
	
	@Column(unique = true)
	private String name;
	private String address;
	
	@OneToMany(mappedBy = "library",cascade = CascadeType.ALL)
	private List<Floor> floorList=new ArrayList<>();
}
