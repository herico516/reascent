package com.reascent.framework.web.listenner;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.beanutils.BeanUtils;

import com.reascent.framework.app.modules.AppBaseModule;
import com.reascent.framework.config.ModuleConfig;
import com.reascent.framework.config.ModuleConfigList;
import com.reascent.framework.config.ModuleProperty;
import com.reascent.framework.digesters.AppModuleDigester;

public class ApplicationModuleLoadListenner implements ServletContextListener {

	private ModuleConfigList configList = new ModuleConfigList();;

	private List<ModuleConfig> moduleConfigs = new ArrayList<ModuleConfig>();
	
	private Map<String,Object> modules = new HashMap<String,Object>();
	
	
	
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {

		System.out.println("ApplicationModuleLoadListenner init... ");

		String path = contextEvent.getServletContext().getInitParameter("appModulleConfig");

		String configRealPath = this.getClass().getClassLoader().getResource(path).getPath();

		System.out.println("Loading the app module config file.["+path+"]");
		AppModuleDigester digester = new AppModuleDigester(configRealPath,configList);
		

		try {
			
			configList = (ModuleConfigList) digester.digester();

			moduleConfigs = configList.getModules();

			for (ModuleConfig moduleConfig : moduleConfigs) {

				String name = moduleConfig.getInitClassName();
				String initClass = moduleConfig.getInitClassName();

				System.out.println("init modue[" + name + "]");

				Object obj =  Class.forName(initClass).newInstance();

				List<ModuleProperty> property =	moduleConfig.getPropertys();
				Map<String,String> proMap = new HashMap<String,String>();
				for (ModuleProperty moduleProperty : property) {
					
					proMap.put(moduleProperty.getKey(), moduleProperty.getValue());
					
				}
				
				BeanUtils.populate(obj, proMap);
				
				modules.put(name, obj);
				
				AppBaseModule module = (AppBaseModule)obj;
				module.init(contextEvent.getServletContext());

			}
			

			
			System.out.println("ContextPath = "+contextEvent.getServletContext().getContextPath());
			System.out.println("ContextPath = "+contextEvent.getServletContext().getContext("/").getContextPath());
			System.out.println("ContextPath = "+contextEvent.getServletContext().getServletContextName());
			
			System.out.println("ApplicationModuleLoadListenner initial succesfully , total "+modules.size()+" modules loaded .");

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {

		if(null != modules && modules.size() > 0){
			
			for (String key :modules.keySet() ) {
				
				Object obj = modules.get(key);
				
				AppBaseModule module = (AppBaseModule)obj;
				
				module.destory(contextEvent.getServletContext());
				System.out.println("destory module ["+key+"]");
			}
		}
		
		System.out.println("ApplicationModuleLoadListenner Destroy , total "+modules.size()+" modules destroyed");
	}

}
