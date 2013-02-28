package com.reascent.framework.config;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.reascent.framework.digesters.SystemVariableDigester;

public class SystemVariable {
	
	private static SystemVariable instance = null;
	
	private List<SystemParameter> parameters = new ArrayList<SystemParameter>();
	
	
	private SystemVariable(){
		
		load();
	}
	
	public static SystemVariable getInstance()
	{
		if(null == instance)
		{
			instance = new SystemVariable();
		}
		return instance;
	}
	
	public  void load()
	{
		
			try{
				
			  System.out.println("start to load the system variable config file...");
				
			  Context initCtx = new InitialContext();

			  Context envCtx = (Context)initCtx.lookup("java:comp/env");
			  
			  String paramValue = (String)envCtx.lookup("sysParam");
			
			  String configPath = SystemVariable.class.getClassLoader().getResource(paramValue).getPath();
			  
			  SystemVariableDigester digester =new SystemVariableDigester(configPath,instance);
			  
			  digester.push(this);
			
			  instance = digester.digester();
			  System.out.println("total parameter size is "+instance.getParameters().size()+".");
			  System.out.println("load the system variable config file successfully.["+paramValue+"]");
			  
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	 
		
	}


	public List<SystemParameter> getParameters() {
		
		return parameters;
	}
	
	public void addParameter(SystemParameter param)
	{
		if( !parameters.contains(param))
		{
			parameters.add(param);
		}
	}
	

}
