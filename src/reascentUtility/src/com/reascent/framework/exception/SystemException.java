package com.reascent.framework.exception;

public class SystemException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public SystemException(){
		super();
	}
	
	
    public SystemException(String msg){
		
    	super(msg);
	}
    
    
 public SystemException(String msg,Throwable cause){
		
    	super(msg,cause);
	}

}
