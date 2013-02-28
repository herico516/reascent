package com.reascent.framework.digesters;

import java.io.File;
import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.reascent.framework.config.ModuleConfig;
import com.reascent.framework.config.ModuleConfigList;
import com.reascent.framework.config.ModuleProperty;
import com.reascent.framework.utill.AppConstant;

public class AppModuleDigester extends Digester {

	private String configPath;

	private ModuleConfigList configList;

	public AppModuleDigester(String configPath, ModuleConfigList configList) {
		this.configPath = configPath;
		this.configList = configList;
	}

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	public ModuleConfigList getConfigList() {
		return configList;
	}

	public void setConfigList(ModuleConfigList configList) {
		this.configList = configList;
	}

	public ModuleConfigList digester() {

		System.out.println("AppModuleDigester digestering...");
		this.setNamespaceAware(true);
		this.setRuleNamespaceURI(AppConstant.APP_MODULE_NAME_SPACE);
		this.setValidating(false);
		this.push(configList);

		this.addObjectCreate("appModules/module", ModuleConfig.class);
		this.addBeanPropertySetter("appModules/module/name", "name");
		this.addBeanPropertySetter("appModules/module/initClassName","initClassName");

		this.addObjectCreate("appModules/module/property",ModuleProperty.class);
		this.addSetProperties("appModules/module/property");

		this.addSetNext("appModules/module/property", "addProperty",ModuleProperty.class.getName());

		this.addSetNext("appModules/module", "addModue", ModuleConfig.class.getName());

		try {

			configList = (ModuleConfigList) this.parse(new File(configPath));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		return configList;

	}

}
