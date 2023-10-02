package com.Library.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> exceptionHandler(Exception e,WebRequest r){
		MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), e.getMessage(), r.getDescription(false));
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<MyErrorDetails> noHandlerFoundException(NoHandlerFoundException e,WebRequest r){
		MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), e.getMessage(), r.getDescription(false));
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<MyErrorDetails> dataIntegrityException(DataIntegrityViolationException e,WebRequest r){
		MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), e.getMessage(), r.getDescription(false));
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails> methodArguementNotValidException(MethodArgumentNotValidException e){
		MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), "Validation Error", e.getBindingResult().getFieldError().getDefaultMessage());
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(StudentException.class)
	public ResponseEntity<MyErrorDetails> studentException(StudentException e,WebRequest r){
		MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), e.getMessage(), r.getDescription(false));
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AdminException.class)
	public ResponseEntity<MyErrorDetails> adminException(AdminException e,WebRequest r){
		MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), e.getMessage(), r.getDescription(false));
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FloorException.class)
	public ResponseEntity<MyErrorDetails> floorException(FloorException e,WebRequest r){
		MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), e.getMessage(), r.getDescription(false));
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SeatException.class)
	public ResponseEntity<MyErrorDetails> seatException(SeatException e,WebRequest r){
		MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), e.getMessage(), r.getDescription(false));
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(LibraryException.class)
	public ResponseEntity<MyErrorDetails> libraryException(LibraryException e,WebRequest r){
		MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), e.getMessage(), r.getDescription(false));
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ShiftException.class)
	public ResponseEntity<MyErrorDetails> shiftException(ShiftException e,WebRequest r){
		MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), e.getMessage(), r.getDescription(false));
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<MyErrorDetails> handleAccessDeniedException(AccessDeniedException ex, WebRequest req) {
		MyErrorDetails err = new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(ex.getMessage());
		err.setDetails(req.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<MyErrorDetails> myIllegalArgumentException(IllegalArgumentException ee,WebRequest req) {

		MyErrorDetails err = new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(ee.getMessage());
		err.setDetails(req.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	
}
