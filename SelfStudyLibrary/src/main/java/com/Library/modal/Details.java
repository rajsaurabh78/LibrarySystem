package com.Library.modal;

import java.time.LocalDate;

import com.Library.Validation.PasswordValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Details {
	
	@NotBlank(message ="Name should not Blank." )
	@NotEmpty(message ="Name should not Empty." )
	@NotNull(message ="Name should not Null." )
	private String name;
	
	@Column(unique = true)
	@Email(message = "Email should be in right format")
	private String email;
	
	@Column(unique = true)
	@NotNull(message ="Mobile No should not Null." )
	@Pattern(regexp = "^[6-9]\\d{9}$",message= "Mobile Number Should be of 10 digits and starts with 6-9")
	private String mobile;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
//	message ="Password must be of atleast 8 characters, contains at least one uppercase, one lowercase,a special character and a number in it.")
	//@PasswordValidation
	private String password;
	
	@NotBlank(message ="Address should not Blank." )
	@NotEmpty(message ="Address should not Empty." )
	@NotNull(message ="Address should not Null." )
	private String address;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Past(message = "DOB should be in past.")
	private LocalDate DOB;

}
