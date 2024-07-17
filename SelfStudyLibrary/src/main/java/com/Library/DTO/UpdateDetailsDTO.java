package com.Library.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class UpdateDetailsDTO {
	
	private String name;
	
	@Column(unique = true)
	@Email(message = "Email should be in right format")
	private String email;
	
	@Column(unique = true)
	@Pattern(regexp = "^[6-9]\\d{9}$",message= "Mobile Number Should be of 10 digits and starts with 6-9")
	private String mobile;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	private String address;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Past(message = "DOB should be in past.")
	private LocalDate DOB;
	
	private String wantedShift;

}
