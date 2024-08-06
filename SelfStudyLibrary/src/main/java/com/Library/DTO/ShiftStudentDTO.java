package com.Library.DTO;

import com.Library.modal.Student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftStudentDTO {
	private SeatDTO seat;
	private Student student;

}
