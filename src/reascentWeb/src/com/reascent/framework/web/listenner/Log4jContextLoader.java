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

package com.reascent.framework.web.listenner;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Herico
 * @deprecated
 */
public class Log4jContextLoader implements ServletContextListener
{

	public static String	LOG4J_CONFIG_PARAMETER_NAME	= "log4j";

	/*
	 * (non-Javadoc)
	 * @seejavax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	public void contextDestroyed ( ServletContextEvent contextEven )
	{

		String contextPath_prefix = contextEven.getServletContext ()
		        .getRealPath ( "/" );

		String prefix = contextEven.getServletContext ().getInitParameter (LOG4J_CONFIG_PARAMETER_NAME );
		
		String filePath = contextPath_prefix+prefix;
		
		System.out.println (filePath);
		
		
		DOMConfigurator.configure (filePath );
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	public void contextInitialized ( ServletContextEvent arg0 )
	{
		

	}

}
