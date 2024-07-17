package com.Library.DTO;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateShiftDTO {
	
	@NotNull(message ="ShiftName should not Null." )
	private Integer shiftId;
	
	private String shiftName;
	
	private LocalTime startTime;
	
	private LocalTime endTime;
	
}

