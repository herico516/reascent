package com.reascent.framework.exception;

public class DAOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public DAOException(){
		super();
	}
	
	
    public DAOException(String msg){
		
    	super(msg);
	}
    
    
    public DAOException(String msg,Throwable cause){
		
    	super(msg,cause);
	}
    
   

}
