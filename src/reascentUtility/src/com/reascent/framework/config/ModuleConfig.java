package com.reascent.framework.config;

import java.util.ArrayList;
import java.util.List;

public class ModuleConfig {
	
	private String name;
	
	private String initClassName;
	
	private List<ModuleProperty> propertys = new ArrayList<ModuleProperty>();

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInitClassName() {
		return initClassName;
	}

	public void setInitClassName(String initClassName) {
		this.initClassName = initClassName;
	}

	public List<ModuleProperty> getPropertys() {
		return propertys;
	}

	public void setPropertys(List<ModuleProperty> propertys) {
		this.propertys = propertys;
	}


	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((initClassName == null) ? 0 : initClassName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModuleConfig other = (ModuleConfig) obj;
		if (initClassName == null) {
			if (other.initClassName != null)
				return false;
		} else if (!initClassName.equals(other.initClassName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void addProperty(ModuleProperty proerty)
	{
		boolean isContain = this.propertys.contains(proerty);
		if(!isContain)
		{
			this.propertys.add(proerty);
		}
	}
	
}
