package com.reascent.framework.config;

import java.util.ArrayList;
import java.util.List;

public class ModuleConfigList {
	
	private List<ModuleConfig> modules = new ArrayList<ModuleConfig>();

	public List<ModuleConfig> getModules() {
		return modules;
	}

	public void setModules(List<ModuleConfig> modules) {
		this.modules = modules;
	}
	
	
	public void addModue(ModuleConfig module)
	{
		boolean isContain = this.modules.contains(module);
		if(!isContain)
		{
			this.modules.add(module);
		}
	}
	
	

}
