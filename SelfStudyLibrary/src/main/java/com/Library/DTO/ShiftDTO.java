package com.Library.DTO;

import java.time.LocalTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftDTO {

	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator ="shift_seq")
	@SequenceGenerator(name="shift_seq", sequenceName="shift_seq",allocationSize=1, initialValue=1)
	private Integer shiftId;
	
	@NotBlank(message ="ShiftName should not Blank." )
	@NotEmpty(message ="ShiftName should not Empty." )
	@NotNull(message ="ShiftName should not Null." )
	private String shiftName;
	
	@NotNull(message ="StartTime should not Null." )
	private LocalTime startTime;
	
	@NotNull(message ="EndTime should not Null." )
	private LocalTime endTime;
	
	@NotNull(message ="noOfSeat should not Null." )
	private Integer noOfSeats;
}

