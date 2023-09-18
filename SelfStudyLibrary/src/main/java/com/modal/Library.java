package com.modal;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String name;
	private String address;
	private List<Floor> floorList;
}
