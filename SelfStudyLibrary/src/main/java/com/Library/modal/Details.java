package com.Library.modal;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Details {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Integer id;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	
	@Column(unique = true)
	private String mobile;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private String address;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate DOB;

}
