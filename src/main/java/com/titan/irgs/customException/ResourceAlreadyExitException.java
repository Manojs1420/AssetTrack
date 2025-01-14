package com.titan.irgs.customException;

@SuppressWarnings("serial")
public class ResourceAlreadyExitException extends RuntimeException{
	
	public ResourceAlreadyExitException() {}
	  
	  public ResourceAlreadyExitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	    super(message, cause, enableSuppression, writableStackTrace);
	  }
	  
	  public ResourceAlreadyExitException(String message, Throwable cause) {
	    super(message, cause);
	  }
	  
	  public ResourceAlreadyExitException(String message) {
	    super(message);
	  }
	  
	  public ResourceAlreadyExitException(Throwable cause) {
	    super(cause);
	  }
}
