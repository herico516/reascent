/**
 * <center> Copyright @ 2011 ,Wootion, Co,espWeb Ltd. All Rights
 * Reserved.</center> <br>
 * <br>
 * <center>Wootion copyrights this specification. No part of this specification
 * may be reproduced in any form or means, without the prior written consent of
 * Wootion.</center> <br>
 * <center>This specification is preliminary and is subject to change at any
 * time without notice. Wootion assumes no responsibility for any errors
 * contained herein.</center> <br>
 */

package com.reascent.framework.web.action;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;
import com.reascent.framework.service.bean.Person;

/**
 * @author Herico
 */
public class LoginAction extends ActionSupport
{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String	          username;

	private String	          password;

	
	public static Log log=( Log ) LogFactory.getLog ( LoginAction.class );
	
	
    /**
     * @return the username
     */
    public String getUsername ()
    {
    	return username;
    }




	
    /**
     * @param username the username to set
     */
    public void setUsername ( String username )
    {
    	this.username = username;
    }




	
    /**
     * @return the password
     */
    public String getPassword ()
    {
    	return password;
    }




	
    /**
     * @param password the password to set
     */
    public void setPassword ( String password )
    {
    	this.password = password;
    }




	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute () throws Exception
	{

		System.out.println ( "This is first action!" );
		log.info ( "***************name=" + username + " password=" + password  );
		log.error( "***************name=" + username + " password=" + password  );
		System.out.println ( "name=" + username + " password=" + password );
		
        ApplicationContext ctx=new ClassPathXmlApplicationContext(new String[]{"reascent\\config\\service\\bean.xml","reascent\\config\\service\\dataAccessContext.xml"});
		
		Person person = (Person)ctx.getBean("person");
		
		person.ssyHello();
		System.out.println(person.getDs().toString());
		
//	    Person person2 = (Person)ctx.getBean("person2");
//		
//		person2.ssyHello();
		
		String message = ctx.getMessage("hello", new String[]{}, Locale.getDefault());
		
		System.out.println("spring message:hello = "+message);
		
		
		String actionmessage = getText("com.msg.test",new String[]{});
		
		System.out.println("action-->message:com.msg.test = "+actionmessage);
		
		
//		DataSource ds = (DataSource)ctx.getBean("dataSource");
//		
//		Connection conn = ds.getConnection();
//		
//		PreparedStatement pstm = conn.prepareStatement("select name ,password from system.test");
//		
//		ResultSet ret = pstm.executeQuery();
//		
//		System.out.println("system.test");
//		System.out.println("------------------------------------------------------------");
//		System.out.println("Name"+"                 "+"Password");
//		while(ret.next())
//		{
//			String name = ret.getString("name");
//			String password = ret.getString("password");
//			
//			System.out.println(name+"                 "+password);
//		}
//		System.out.println("");
//		System.out.println("total records : "+ret.getFetchSize());
//		
//		pstm.close();
//		conn.close();
		
		
//		Context initctx = new InitialContext();
//		
//		Context envCtx = (Context) initctx.lookup("java:comp/env");
//
//		DataSource ds = (DataSource)envCtx.lookup("jdbc/reas");
//		
//		Connection conn = ds.getConnection("system","admin");
//		
//		PreparedStatement pstm = conn.prepareStatement("select name ,password from system.test");
//		
//		ResultSet ret = pstm.executeQuery();
//		
//		System.out.println("system.test");
//		System.out.println("------------------------------------------------------------");
//		System.out.println("Name"+"                 "+"Password");
//		while(ret.next())
//		{
//			String name = ret.getString("name");
//			String password = ret.getString("password");
//			
//			System.out.println(name+"                 "+password);
//		}
//		System.out.println("");
//		System.out.println("total records : "+ret.getFetchSize());
//		
//		pstm.close();
//		conn.close();


		return "success";
	}

}
