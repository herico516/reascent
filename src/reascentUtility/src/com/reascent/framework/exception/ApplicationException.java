package com.reascent.framework.exception;

public class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public ApplicationException(){
		super();
	}
	
	
    public ApplicationException(String msg){
		
    	super(msg);
	}
    
    
 public ApplicationException(String msg,Throwable cause){
		
    	super(msg,cause);
	}

}
