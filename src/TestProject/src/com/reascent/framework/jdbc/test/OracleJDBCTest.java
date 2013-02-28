package com.reascent.framework.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleJDBCTest {
	
	
	public static void main(String[] args) {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:reascent","system","admin");
			
			PreparedStatement pstm = conn.prepareStatement("select name ,password from system.test");
			
			ResultSet ret = pstm.executeQuery();
			
			System.out.println("system.test");
			System.out.println("------------------------------------------------------------");
			System.out.println("Name"+"                 "+"Password");
			while(ret.next())
			{
				String name = ret.getString("name");
				String password = ret.getString("password");
				
				System.out.println(name+"                 "+password);
			}
			System.out.println("");
			System.out.println("total records : "+ret.getFetchSize());
			
			pstm.close();
			conn.close();
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
