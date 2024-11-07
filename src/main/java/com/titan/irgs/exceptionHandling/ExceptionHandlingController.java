package com.titan.irgs.exceptionHandling;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.titan.irgs.ExceptionResponse.ExceptionResponse;
import com.titan.irgs.customException.ResourceAlreadyExitException;
import com.titan.irgs.customException.ResourceNotFoundException;


@ControllerAdvice
public class ExceptionHandlingController {
	
	
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler({ResourceNotFoundException.class})
	   public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest wb) {
	     ExceptionResponse exceptionResponse = new ExceptionResponse();
	     exceptionResponse.setErrorMessage(ex.getLocalizedMessage());
	     return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	   }
	   
	   @ExceptionHandler({ResourceAlreadyExitException.class})
	   public ResponseEntity<Object> handleResourceAlreadyExitException(ResourceAlreadyExitException ex, HttpServletRequest httpServletRequest) {
	     ExceptionResponse exceptionResponse = new ExceptionResponse();
	     exceptionResponse.setErrorMessage(ex.getLocalizedMessage());
	     exceptionResponse.callerURL(httpServletRequest.getRequestURI());
	     return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	   }
	   
	   @ExceptionHandler({AccessDeniedException.class})
	   public ResponseEntity<Object> accessDeniedException(Exception ex, HttpServletRequest httpServletRequest) {
	     ExceptionResponse exceptionResponse = new ExceptionResponse();
	     exceptionResponse.setErrorMessage(ex.getLocalizedMessage());
	     exceptionResponse.callerURL(httpServletRequest.getRequestURI());
	     return new ResponseEntity<Object>(exceptionResponse, HttpStatus.FORBIDDEN);
	   }
	   
	  

	   
	   
	   @ExceptionHandler({Exception.class})
	   public ResponseEntity<Object> globalException(Exception ex, HttpServletRequest httpServletRequest) {
	     ExceptionResponse exceptionResponse = new ExceptionResponse();
	     exceptionResponse.setErrorMessage(ex.getLocalizedMessage());
	     exceptionResponse.callerURL(httpServletRequest.getRequestURI());
	     return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	   }

}
