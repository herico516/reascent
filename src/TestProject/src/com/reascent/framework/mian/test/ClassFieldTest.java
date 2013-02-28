package com.reascent.framework.mian.test;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Iterator;

public class ClassFieldTest {
	
	public static int TEST =4;
	
	public int test = 6;
	
	
	
	
	
	public static void main(String[] args) 
	{
//		Class c = ClassFieldTest.class;
//		
//		Field[] fields = c.getFields();
//		
//		
//		try {
//			for (int i = 0; i < fields.length; i++)
//			{
//				int fieldvalue = fields[i].getInt(c);
//				
//				System.out.println("name="+fields[i].getName()+"   value="+fieldvalue);
//				
//			}
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Class c = ResultSet.class;
		
		Method [] methods = c.getDeclaredMethods();
		
		for (Method method : methods) 
		{
			if(method.getName().indexOf("get")>=0)
			{
			   System.out.println(method.toString());
			}
		}
		
		
	}

}
