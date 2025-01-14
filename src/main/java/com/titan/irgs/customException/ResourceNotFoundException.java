package com.titan.irgs.customException;

public class ResourceNotFoundException extends RuntimeException {
	  public ResourceNotFoundException() {}
	  
	  public ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	    super(message, cause, enableSuppression, writableStackTrace);
	  }
	  
	  public ResourceNotFoundException(String message, Throwable cause) {
	    super(message, cause);
	  }
	  
	  public ResourceNotFoundException(String message) {
	    super(message);
	  }
	  
	  public ResourceNotFoundException(Throwable cause) {
	    super(cause);
	  }

}
