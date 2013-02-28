package com.reascent.framework.db.helper;


public class DBConstant {
	
public static String DEFAULT_SCHEMA = "res";
	
	public static String DEFAULT_DATA_SOURCE = "jdbc/reas";
	
	public static String PREFIX_DATASOURCE = "java:comp/env/";

	public static String SET_ORACLE_SCHEMA_SQL = "alter session set current_schema = ";

	public static String SET_DB2_SCHEMA_SQL = "set current schema ";
	
	public static String SET_MYSQL_SCHEMA_SQL = "use database  ";

	public static String ORACLE_DBTYPE = "oracle";

	public static String DB2_DBTYPE = "db2";
	
	public static String MYSQL_DBTYPE = "mysql";

	public static String UNKNOW_DBTYPE = "unknow";
	
	


}
