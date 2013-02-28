package com.reascent.framework.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport implements
		ActionInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Log log=( Log ) LogFactory.getLog ( BaseAction.class );

	@Override
	public String postAction() throws Exception
	{
		return null;
	}

	@Override
	public String preAction() throws Exception 
	{
		return null;
	}

	@Override
	public final String execute() throws Exception 
	{
		String result = "";
		//implements the function before executing the functions of the struts action
		log.debug("Execute the function before the action...");
		String preForward = preAction();
		if(preForward != null && preForward.trim().length()>0)
		{
			result = preForward;
		}
		log.debug("Execute the function before the action completed.");
		//execute the action
		
		String forward = null;
		
		try 
		{
			log.debug("Execute the function of the action...");
			forward = doExcute();
			if(forward != null || forward.trim().length()>0)
			{
				log.debug("Execute the function of the action compaleted.");
				result =  forward;
			}
			
		} catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		
		log.debug("Execute the function after the action...");
		String postForward = postAction();
		if(postForward != null && postForward.trim().length()>0)
		{
			log.debug("Execute the function after the action completed.");
			result = postForward;
		}
		
		return result;
	}
	

	public abstract String doExcute() throws Exception;

}
