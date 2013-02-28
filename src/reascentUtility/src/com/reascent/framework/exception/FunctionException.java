package com.reascent.framework.exception;

public class FunctionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public FunctionException(){
		super();
	}
	
	
    public FunctionException(String msg){
		
    	super(msg);
	}
    
    
    public FunctionException(String msg,Throwable cause){
		
    	super(msg,cause);
	}

}
