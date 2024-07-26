package com.Library.modal;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"shiftName", "floorId"}))
public class Shift {
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="shift_seq")
	@SequenceGenerator(name="shift_seq", sequenceName="shift_seq",allocationSize=1, initialValue=1)
	private Integer shiftId;
	
//	@NotBlank(message ="ShiftName should not Blank." )
//	@NotEmpty(message ="ShiftName should not Empty." )
//	@NotNull(message ="ShiftName should not Null." )
	@Enumerated(EnumType.STRING)
	private ShiftName shiftName;
	
//	@NotBlank(message ="StartTime should not Blank." )
//	@NotEmpty(message ="StartTime should not Empty." )
	@NotNull(message ="StartTime should not Null." )
	private LocalTime startTime;
	
//	@NotBlank(message ="EndTime should not Blank." )
//	@NotEmpty(message ="EndTime should not Empty." )
	@NotNull(message ="EndTime should not Null." )
	private LocalTime endTime;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="floorId")
	private Floor floor;
	
	@OneToMany(mappedBy = "shift",cascade =  CascadeType.ALL,orphanRemoval = false)
	private List<Seat> seatList=new ArrayList<>();
	
}
