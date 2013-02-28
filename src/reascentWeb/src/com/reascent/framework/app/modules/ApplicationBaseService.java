package com.reascent.framework.app.modules;

import java.util.List;

import javax.servlet.ServletContext;

import com.reascent.framework.config.SystemParameter;
import com.reascent.framework.config.SystemVariable;


public class ApplicationBaseService implements AppBaseModule {

	private String appCode ="reascent";
	
	private String menuType = "left_accordion";
	
	private String isMDI = "false";
	

	@Override
	public boolean init(ServletContext context) {
		
		System.out.println("ApplicationBaseService init..");
		
		System.out.println("appCode="+this.getAppCode()+"  menuType="+this.getMenuType()+"   isMDI="+this.getIsMDI());
		
		initSystemParameter();
		
		return false;
	}


	
	@Override
	public boolean destory(ServletContext context) {
		
		System.out.println("ApplicationBaseService destory..");
		return false;
	}


	private boolean initSystemParameter()
	{
		System.out.println("start init system parameters...");
		
        SystemVariable  sysParamCache = SystemVariable.getInstance();

		List<SystemParameter> params = sysParamCache.getParameters();
		
		 for (SystemParameter param : params) 
		 {
			 if(param != null)
			 {
				 System.out.println(" parameter : name="+param.getName() +"  value="+param.getValue());
		     }
			
		}
		
		 System.out.println(" init system parameters successfully.");
		 return true;
		
	}

	public String getAppCode() {
		return appCode;
	}



	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}



	public String getMenuType() {
		return menuType;
	}



	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}



	public String getIsMDI() {
		return isMDI;
	}



	public void setIsMDI(String isMDI) {
		this.isMDI = isMDI;
	}
	


}
