package com.Library.modal;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Admin extends Details{
	
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="admin_seq")
	@SequenceGenerator(name="admin_seq", sequenceName="admin_seq",allocationSize=1, initialValue=1)
	private Integer id;
	
	@OneToMany(mappedBy = "admin",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Authority> authorities=new ArrayList<>();

}
