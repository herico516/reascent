package com.reascent.framework.Ioc.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.reascent.framework.service.bean.Person;

public class SpringIOCTest {
	
	public static void main(String[] args) {
		
		ApplicationContext ctx=new ClassPathXmlApplicationContext("reascent/config/service/dataAccessContext.xml");
		
		Person person = (Person)ctx.getBean("person");
		
		person.ssyHello();
		
		System.out.println(person.getDs().toString());
		
	}

}
