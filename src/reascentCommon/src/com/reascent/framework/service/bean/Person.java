package com.reascent.framework.service.bean;

import javax.sql.DataSource;

public class Person {
	
	private String name;
	
	private int age;
	
	private String address;

	public String getName() {
		return name;
	}
	
	private DataSource ds;
	

	public DataSource getDs() {
		return ds;
	}

	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	public void ssyHello()
	{
		System.out.println("Hello, my name is "+name+", I am from "+address);
	}

}
