package com.Library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Library.modal.Seat;

public interface SeatRepository extends JpaRepository<Seat, Integer>{
	
	@Query(value="select st.seat_no ,st. floor ,st.shift_id ,st.student_id from seat st inner join shift sf on(st.shift_id=sf.shift_id) where sf.shift_name ='first' and st.student_id is null limit 1",nativeQuery = true)
	Seat getFirstShiftSeat();
	@Query(value="select st.seat_no ,st. floor ,st.shift_id ,st.student_id from seat st inner join shift sf on(st.shift_id=sf.shift_id) where sf.shift_name ='second' and st.student_id is null limit 1",nativeQuery = true)
	Seat getSecondSeat();
	@Query(value="select st.seat_no ,st. floor ,st.shift_id ,st.student_id from seat st inner join shift sf on(st.shift_id=sf.shift_id) where sf.shift_name ='third' and st.student_id is null limit 1",nativeQuery = true)
	Seat getThirdSeat();
	
	@Query(value="select * from seat where floor =? and student_id is not null",nativeQuery =true )
	List<Seat> allSeatsByFloor(Integer floor);
	
}
//@Query("SELECT s FROM Seat s " +
//        "JOIN s.shift sh " +
//        "WHERE sh.shiftName = 'first' AND s.studentId IS NULL " +
//        "LIMIT 1")
//select s from seat st inner join shift sf on(st.shift_id=sf.shift_id) where st.student_id is null
