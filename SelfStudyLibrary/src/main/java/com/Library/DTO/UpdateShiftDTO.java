package com.Library.DTO;

import java.time.LocalTime;

import com.Library.modal.ShiftName;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	
	@Enumerated(EnumType.STRING)
	private ShiftName shiftName;
	
	private LocalTime startTime;
	
	private LocalTime endTime;
	
}

