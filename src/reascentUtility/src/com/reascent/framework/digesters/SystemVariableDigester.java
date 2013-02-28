package com.reascent.framework.digesters;

import java.io.File;
import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.reascent.framework.config.SystemParameter;
import com.reascent.framework.config.SystemVariable;

public class SystemVariableDigester extends Digester{
	
	
	private String configPath;
	
	private SystemVariable sysVariable;
	
	

	
	public SystemVariableDigester(String configPath,SystemVariable sysVariable )
	{
		this.configPath = configPath;
		this.sysVariable = sysVariable;
	}

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}
	
	public SystemVariable getSysVariable() {
		return sysVariable;
	}

	public void setSysVariable(SystemVariable sysVariable) {
		this.sysVariable = sysVariable;
	}

	public SystemVariable digester(){
		
		  this.setValidating(false);
		  this.addObjectCreate("sysParameters/parameter", SystemParameter.class);
		  this.addBeanPropertySetter("sysParameters/parameter/name","name");
		  this.addBeanPropertySetter("sysParameters/parameter/value","value");
		  this.addSetNext("sysParameters/parameter", "addParameter",SystemParameter.class.getName());
			  
			System.out.println("SystemVariableDigester digestering... ");
			
				try {
					
					sysVariable = (SystemVariable) this.parse(new File(configPath));

				} catch (IOException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				}
			
		
		return sysVariable;
		
	}
	

}
