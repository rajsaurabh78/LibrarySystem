package com.Library.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Authority {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="authority_seq")
//	@SequenceGenerator(name="authority_seq", sequenceName="authority_seq",allocationSize=1, initialValue=700)
	private Integer id;
	
	@NotBlank(message ="Name should not Blank." )
	@NotEmpty(message ="Name should not Empty." )
	@NotNull(message ="Name should not Null." )
	private String name;
	
	@ManyToOne
	@JsonIgnore
	private Student user;
	
	
}
