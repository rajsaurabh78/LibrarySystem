package com.Library.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
	
	private String name;
	private Integer userId;
	private Boolean payment;
	private String wantedShift;
	private String providedShift;
	
}

