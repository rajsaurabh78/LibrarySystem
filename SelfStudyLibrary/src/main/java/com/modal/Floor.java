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
@NoArgsConstructor
@AllArgsConstructor
public class Floor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer floorId;
	private String name;
	private List<Shift> shiftList;
}
