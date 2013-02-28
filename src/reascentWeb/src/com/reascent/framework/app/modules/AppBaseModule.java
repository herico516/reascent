package com.reascent.framework.app.modules;

import javax.servlet.ServletContext;

public interface AppBaseModule {
	
	public boolean init(ServletContext context);
	
	public boolean destory(ServletContext context);

}
