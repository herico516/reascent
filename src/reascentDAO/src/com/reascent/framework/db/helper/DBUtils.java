package com.reascent.framework.db.helper;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.reascent.framework.exception.DBException;

public class DBUtils {

	public static Log log = (Log) LogFactory.getLog(DBUtils.class);
	
	private DataSource ds = null;

	private Connection conn = null;

	private PreparedStatement pstm = null;

	private ResultSet resultSet = null;

	private String currentSchema = "res";

	private String currentDBType = null;
	
	private String dbVersion = null;

	public static  Map<String, DataSource> dsCache = new HashMap<String, DataSource>();
	
	private boolean needCache = false;

	public DBUtils() 
	{
       this(DBConstant.DEFAULT_DATA_SOURCE,DBConstant.DEFAULT_SCHEMA,false);
	}

	public DBUtils(String dataSource, String schema, boolean isCache) 
	{
		this.needCache = isCache;
		if(this.needCache)
		{
			this.ds = (DataSource)dsCache.get(dataSource);
		}
		if(this.ds == null)
		{
			this.ds = getDataSource(dataSource, this.needCache);
		}
		
		if (null == this.ds) 
		{
			log.error("The error occurred by getting the DataSource ");
		}

		if (null == schema || "".equals(schema) || schema.trim().length() <= 0) 
		{
			log.debug("The input schema is null or empty,the default value of the current schema is "+ DBConstant.DEFAULT_SCHEMA);
			this.currentSchema = DBConstant.DEFAULT_SCHEMA;
		} 
		else
		{
			this.currentSchema = schema;
		}

		try 
		{

			boolean isOK = setCurrentSchema(ds, schema);
	
			if (isOK) 
			{
				log.debug("Set the schema success, the current schema is "+ this.currentSchema);
			}
			this.conn = this.ds.getConnection();

		} 
		catch (SQLException e)
		{
			log.debug("Couldn't apply the connection from DB, please review the data source config or connect securty.");
			e.printStackTrace();
		} 
		catch (DBException e) 
		{
			log.debug("Couldn't apply the connection from DB, please review the data source config or connect securty.");
			e.printStackTrace();
		}
		
		
		if (this.needCache) 
		{
			dsCache.put(dataSource, this.ds);
		}


	}

	public DBUtils(String dataSource, String schema) 
	{
		this(dataSource, schema, false);
	}

	public DBUtils(String dataSource)
	{
		this(dataSource, DBConstant.DEFAULT_SCHEMA, false);
	}

	public DBUtils(DataSource dataSource, String schema, boolean isCache) 
	{
		this.needCache = isCache;
		
		if (dataSource != null) 
		{
			this.ds = dataSource;

			if (null == schema || "".equals(schema)|| schema.trim().length() <= 0) 
			{
				log.debug("The schema is null or empty,the default value of the current schema is "+ DBConstant.DEFAULT_SCHEMA);
				this.currentSchema = DBConstant.DEFAULT_SCHEMA;
			} 
			else 
			{
				this.currentSchema = schema;
			}

			try 
			{

				boolean isOK = setCurrentSchema(ds, schema);

				if (isOK) 
				{
					log.debug("Set the schema success, the current schema is "+ this.currentSchema);
				}
				this.conn = this.ds.getConnection();

			} 
			catch (SQLException e) 
			{
				log.error("Couldn't apply the connection from DB, please review the data source config or connect securty.");
				e.printStackTrace();
			}
			catch (DBException e) 
			{
				log.error("Couldn't apply the connection from DB, please review the data source config or connect securty.");
				e.printStackTrace();
			}

			if (this.needCache) 
			{
				dsCache.put(dataSource.toString(), dataSource);
			}

		} 
		else
		{
			log.error("The input DataSource are null or invalid. ");
		}

	}

	public DBUtils(DataSource dataSource, String schema) 
	{
		this(dataSource, schema, false);
	}

	public DBUtils(DataSource dataSource)
	{
		this(dataSource, DBConstant.DEFAULT_SCHEMA, false);
	}

	public DBUtils(Connection conn) 
	{
			this.conn = conn;
			try {
				setCurrentSchema(conn,DBConstant.DEFAULT_SCHEMA);
			} 
			catch (DBException e) 
			{
				log.error("Could not connect the target db."+"\n"+e.getMessage());
				e.printStackTrace();
			}	
	}

	public DBUtils(String url, String user, String password, String schema,String driverClass) 
	{
		
		if(log.isDebugEnabled())
		{
			log.debug("The DB host name:"+url);
			log.debug("The DB driver class:"+driverClass);
			log.debug("The DB current schema:"+schema);
		}
		
		try 
		{
			Class.forName(driverClass);
			this.conn = DriverManager.getConnection(url, user, password);
			setCurrentSchema(conn, schema);
		} 
		catch (ClassNotFoundException e) 
		{
			log.error("Could not find the driver class:"+e.getMessage());
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			log.error("Could not connect the target db:"+url+"\n"+e.getMessage());
			e.printStackTrace();
		} 
		catch (DBException e) 
		{
			log.error("Could not connect the target db:"+url+"\n"+e.getMessage());
			e.printStackTrace();
		}

	}

	public DBUtils(String url, String user, String pasword,String driverClass)
	{
		this(url, user, pasword, DBConstant.DEFAULT_SCHEMA,driverClass);
	}

	private DataSource getDataSource(String dataSource, boolean isCache) 
	{

		InitialContext initctx = null;
		DataSource resDatasource = null;

		if (isCache) 
		{
			boolean iscontaint = dsCache.containsKey(dataSource);
			if (iscontaint) {
				resDatasource = dsCache.get(dataSource);
				log.debug("Find the [" + dataSource+ "] data source in the cache.");
			}
		}

		if (null == resDatasource) 
		{
			try {

				initctx = new InitialContext();

				if (dataSource.indexOf("java:comp/env/") < 0) 
				{
					resDatasource = (DataSource) initctx.lookup(DBConstant.PREFIX_DATASOURCE + dataSource.trim());
				} 
				else 
				{
					resDatasource = (DataSource) initctx.lookup(dataSource);
				}

			} catch (NamingException e) 
			{

				e.printStackTrace();
				log.error("Couldn't find the DataSource for the Jndi"+ dataSource, e);
				log.debug("Please check the datasource config in web application server.");
			}
		}

		if (isCache) 
		{
			dsCache.put(dataSource, resDatasource);
		}

		return resDatasource;
	}

	/**
	 * set the current schema according to the DB type
	 * @param ds
	 * @param schema
	 * @return
	 */
	private boolean setCurrentSchema(DataSource ds, String schema) throws DBException
	{

		boolean success = false;
		try {
			Connection testConn = ds.getConnection();
			PreparedStatement testPstm = null;

			DatabaseMetaData metaData = testConn.getMetaData();
			String DBtype = metaData.getDatabaseProductName();
			String DBVersion = metaData.getDatabaseProductVersion();
			
			setCurrentDBType(DBtype);
			setDbVersion(DBVersion);
			
			
			if (null != getCurrentDBType()&& getCurrentDBType().trim().length() > 0)
			{
				if (DBConstant.ORACLE_DBTYPE.equals(getCurrentDBType())) 
				{
					testPstm = testConn.prepareStatement(DBConstant.SET_ORACLE_SCHEMA_SQL+ schema);
					success = testPstm.execute();
				}
				else if (DBConstant.DB2_DBTYPE.equals(getCurrentDBType())) 
				{
					testPstm = testConn.prepareStatement(DBConstant.SET_DB2_SCHEMA_SQL+ schema);
					success = testPstm.execute();
				} 
				else if (DBConstant.MYSQL_DBTYPE.equals(getCurrentDBType())) 
				{
					testPstm = testConn.prepareStatement(DBConstant.SET_MYSQL_SCHEMA_SQL + schema);
					success = testPstm.execute();
				} 
				else 
				{
					success = false;
					setCurrentDBType(DBConstant.UNKNOW_DBTYPE);
					log.debug("The target DB doesn't support the schema .");
				}
			} 
			
		} 
		catch (SQLException e) 
		{
			log.error("Failed to set the schema. ", e);
			throw new DBException("Failed to set the schema.",e);
		}

		return success;
	}

	private boolean setCurrentSchema(Connection conn, String schema) throws DBException
	{

		boolean success = false;
		try {
			
			PreparedStatement testPstm = null;

			DatabaseMetaData metaData = conn.getMetaData();
			String DBtype = metaData.getDatabaseProductName();
			String DBVersion = metaData.getDatabaseProductVersion();
			
			setCurrentDBType(DBtype);
			setDbVersion(DBVersion);
			
			
			if (null != getCurrentDBType()&& getCurrentDBType().trim().length() > 0)
			{
				if (DBConstant.ORACLE_DBTYPE.equals(getCurrentDBType())) 
				{
					testPstm = conn.prepareStatement(DBConstant.SET_ORACLE_SCHEMA_SQL+ schema);
					success = testPstm.execute();
				}
				else if (DBConstant.DB2_DBTYPE.equals(getCurrentDBType())) 
				{
					testPstm = conn.prepareStatement(DBConstant.SET_DB2_SCHEMA_SQL+ schema);
					success = testPstm.execute();
				} 
				else if (DBConstant.MYSQL_DBTYPE.equals(getCurrentDBType())) 
				{
					testPstm = conn.prepareStatement(DBConstant.SET_MYSQL_SCHEMA_SQL+ schema);
					success = testPstm.execute();
				} 
				else 
				{
					success = false;
					setCurrentDBType(DBConstant.UNKNOW_DBTYPE);
					log.debug("The target DB doesn't support the schema .");
				}
			} 
			
		} catch (SQLException e) {
			log.error("Failed to set the schema. ", e);
			throw new DBException("Failed to set the schema.",e);
		}

		return success;
	}
	
	
	public boolean setSchema(String schema) 
	{

		boolean success = false;
		
			try
			{
					if(this.ds!= null)
					{
						success = setCurrentSchema(ds,  schema);
					}
					
					if(success)
					{
						return success;
					}
					
					if(this.conn!=null)
					{
						success = setCurrentSchema(conn,  schema);
					}
					
					if(!success)
					{
						log.error("Failed to set the schema. ");
					}
			} 
			catch (DBException e) 
			{
				log.error("Failed to set the schema. ");
				e.printStackTrace();
			}
			
		return success;
	}
	
	
	
	public void setNeedCache(boolean needCache) 
	{
		this.needCache = needCache;
	}

	private String getCurrentDBType() 
	{
		return this.currentDBType;
	}

	/**
	 * set current schema according to the database type
	 * @param currentDBType
	 */
	private void setCurrentDBType(String currentDBType) 
	{

		if (currentDBType.toLowerCase().indexOf(DBConstant.ORACLE_DBTYPE) >= 0) 
		{
			this.currentDBType = DBConstant.ORACLE_DBTYPE;
		} 
		else if (currentDBType.toLowerCase().indexOf(DBConstant.DB2_DBTYPE) >= 0) 
		{
			this.currentDBType = DBConstant.DB2_DBTYPE;
		}
		else if(currentDBType.toLowerCase().indexOf(DBConstant.MYSQL_DBTYPE) >= 0)
		{
			this.currentDBType = DBConstant.MYSQL_DBTYPE;
		}
		else 
		{
			this.currentDBType = DBConstant.UNKNOW_DBTYPE;
		}
	}

	/**
	 * get the version of the data base
	 * @return
	 */
	public String getDbVersion() {
		return dbVersion;
	}

	/**
	 * set the version of the current data base
	 * @param dbVersion
	 */
	public void setDbVersion(String dbVersion) {
		this.dbVersion = dbVersion;
	}
    /**
     * get the connection of the current data source
     * @return
     */
	public Connection getConn() {
		return conn;
	}
    /**
     * set the connection
     * @param conn
     */
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	 /**
	  * set commit style
	  * @param isCommit
	  */
	public void setAutoCommit(boolean isCommit) 
	{
		try {

			this.conn.setAutoCommit(isCommit);

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("Could modify the style of the commit.", e);
		}
	}
	
	 /**
	    * get the result set  
	    * @return
	    */
		
	public ResultSet getResultSet()
	{
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet)
	{
		this.resultSet = resultSet;
	}
	
	
	public boolean buildSQLStatement(String sql,Object[] params) throws DBException
	{
		
		try
		{
			this.pstm = this.conn.prepareStatement(sql);
			
			if(params !=null && params.length>0)
			{
				for (int i=0;i< params.length;i++) 
				{
					if(params[i]==null)
					{
						this.pstm.setNull(i, Types.NULL);
					}
					this.pstm.setObject(i,params[i]);
				}
			}
			
		} 
		catch (SQLException e)
		{
			log.error("Could not build the sql statement.");
			throw new DBException("Could not build the sql statement.",e);
		}
		
		
		return true;
	}
	
	
	public boolean buildSQLStatement(String sql,Object[] params,int fetchSize) throws DBException
	{
		
		try
		{
			this.pstm = this.conn.prepareStatement(sql);
			this.pstm.setFetchSize(fetchSize);
			if(params !=null && params.length>0)
			{
				for (int i=0;i< params.length;i++) 
				{
					if(params[i]==null)
					{
						this.pstm.setNull(i, Types.NULL);
					}
					this.pstm.setObject(i,params[i]);
				}
			}
			
		} 
		catch (SQLException e)
		{
			log.error("Could not build the sql statement.");
			throw new DBException("Could not build the sql statement.",e);
		}
		
		
		return true;
	}
	
	
	public int executeUpdate(String sql,Object[] params)throws DBException
	{
		boolean effected = buildSQLStatement(sql,params);
		int result =0;
		if(effected)
		{
			try 
			{
				result = this.pstm.executeUpdate();
			} 
			catch (SQLException e) 
			{
				log.error("Failed to execute update function. ");
				throw new DBException("Failed to execute update function. ",e);
			}
		}
	
		return result;
		
	}
	
	public int executeUpdate(String sql)throws DBException
	{
		return executeUpdate(sql,null);
	}
	
	
	public int executeQuery(String sql,Object[] params,int fetchSize)throws DBException
	{
		boolean effected = buildSQLStatement(sql,params,fetchSize);
		int result =0;
		if(effected)
		{
			try 
			{
				this.resultSet = this.pstm.executeQuery();
				result = this.resultSet.getFetchSize();
			} 
			catch (SQLException e) 
			{
				log.error("Failed to execute update function. ");
				throw new DBException("Failed to execute update function. ",e);
			}
		}
	
		return result;
		
	}
	
	public int executeQuery(String sql,Object[] params)throws DBException
	{
		boolean effected = buildSQLStatement(sql,params);
		int result =0;
		if(effected)
		{
			try 
			{
				this.resultSet = this.pstm.executeQuery();
				result = this.resultSet.getFetchSize();
			} 
			catch (SQLException e) 
			{
				log.error("Failed to execute update function. ");
				throw new DBException("Failed to execute update function. ",e);
			}
		}
	
		return result;
		
	}
	
	public int executeQuery(String sql)throws DBException
	{
		return executeQuery(sql,null);
	}
	
	public boolean nextRecord()throws DBException
	{
		boolean result = false;
		if(this.resultSet !=null)
		{
			try 
			{
				result = this.resultSet.next();
			} 
			catch (SQLException e) 
			{
				log.error("ResultSet is null, please execute the sql statement. ");
				throw new DBException("ResultSet is null, please execute the sql statement. ",e);
			}
		}
		return result;
	}
	
	
	
	

	/**
	 * roll back the db when need
	 */
	public void rollback() {

		try {

			this.conn.rollback();
		} 
		catch (SQLException e)
		{
			log.error("Roll back error ,function aborted.", e);
			e.printStackTrace();
		}
	}
/**
 * disconnection if useless
 * @throws SQLException
 */
	private void closeConnection() throws SQLException {

		if (null != this.conn) {
			if (!this.conn.isClosed()) {
				this.conn.close();
			}
		}
	}

	/**
	 * release the statement and resultSet
	 * @throws SQLException
	 */
	private void closeStatement() throws SQLException 
	{
		if (null != this.resultSet) {
			if (!this.resultSet.isClosed()) {
				this.resultSet.close();
				this.resultSet = null;
			}
		}

		if (null != this.pstm) {
			if (!this.pstm.isClosed()) {
				this.pstm.close();
				this.pstm = null;
			}
		}
	}
	
	
	/**
	 * close all the connect resource
	 * @throws SQLException
	 */
	public void close() throws DBException
	{
		try 
		{
			closeStatement();
			closeConnection();
			
		} 
		catch (SQLException e) 
		{
			log.error("Failed to close the connection.", e);
			throw new DBException("",e);
		}
	}
	
	
	
	
	public  Object getObject(int colIndex ) throws SQLException
	{
	 return this.resultSet.getObject(colIndex);
	}
	
	public  Object getObject(String colName ) throws SQLException
	{
	 return this.resultSet.getObject(colName);
	}
	
	
	public  boolean getBoolean(int colIndex ) throws SQLException
	{
	 return this.resultSet.getBoolean(colIndex);
	}
	public  boolean getBoolean(String colName ) throws SQLException
	{
	 return this.resultSet.getBoolean(colName);
	}
	
	
	public  byte getByte(int colIndex ) throws SQLException
	{
	 return this.resultSet.getByte(colIndex);
	}
	public  byte getByte(String colName ) throws SQLException
	{
	 return this.resultSet.getByte(colName);
	}
	
	
	public  short getShort(int colIndex ) throws SQLException
	{
	 return this.resultSet.getShort(colIndex);
	}
	public  short getShort(String colName ) throws SQLException
	{
	 return this.resultSet.getShort(colName);
	}
	
	
	public  int getInt(String colName ) throws SQLException
	{
	 return this.resultSet.getInt(colName);
	}
	public  int getInt(int colIndex ) throws SQLException
	{
	 return this.resultSet.getInt(colIndex);
	}
	
	
	public  long getLong(int colIndex ) throws SQLException
	{
	 return this.resultSet.getLong(colIndex);
	}
	public  long getLong(String colName ) throws SQLException
	{
	 return this.resultSet.getLong(colName);
	}
	
	
	public  float getFloat(int colIndex ) throws SQLException
	{
	 return this.resultSet.getFloat(colIndex);
	}
	public  float getFloat(String colName ) throws SQLException
	{
	 return this.resultSet.getFloat(colName);
	}
	
	
	public  double getDouble(String colName ) throws SQLException
	{
	 return this.resultSet.getDouble(colName);
	}
	public  double getDouble(int colIndex ) throws SQLException
	{
	 return this.resultSet.getDouble(colIndex);
	}
	
	
	public  byte[] getBytes(int colIndex ) throws SQLException
	{
	 return this.resultSet.getBytes(colIndex);
	}
	public  byte[] getBytes(String colName ) throws SQLException
	{
	 return this.resultSet.getBytes(colName);
	}
	public  Array getArray(int colIndex ) throws SQLException
	{
	 return this.resultSet.getArray(colIndex);
	}
	public  Array getArray(String colName ) throws SQLException
	{
	 return this.resultSet.getArray(colName);
	}
	public  URL getURL(int colIndex ) throws SQLException
	{
	 return this.resultSet.getURL(colIndex);
	}
	public  URL getURL(String colName ) throws SQLException
	{
	 return this.resultSet.getURL(colName);
	}

	public  String getString(int colIndex ) throws SQLException
	{
	 return this.resultSet.getString(colIndex);
	}
	public  String getString(String colName ) throws SQLException
	{
	 return this.resultSet.getString(colName);
	}
	public  Ref getRef(int colIndex ) throws SQLException
	{
	 return this.resultSet.getRef(colIndex);
	}
	public  Ref getRef(String colName ) throws SQLException
	{
	 return this.resultSet.getRef(colName);
	}
	public  Date getDate(int colIndex ,Calendar cal) throws SQLException
	{
	 return this.resultSet.getDate(colIndex,cal);
	}
	public  Date getDate(String colName ) throws SQLException
	{
	 return this.resultSet.getDate(colName);
	}
	public  Date getDate(String colName ,Calendar cal) throws SQLException
	{
	 return this.resultSet.getDate(colName,cal);
	}
	public  Date getDate(int colIndex ) throws SQLException
	{
	 return this.resultSet.getDate(colIndex);
	}
	public  InputStream getAsciiStream(int colIndex ) throws SQLException
	{
	 return this.resultSet.getAsciiStream(colIndex);
	}
	public  InputStream getAsciiStream(String colName ) throws SQLException
	{
	 return this.resultSet.getAsciiStream(colName);
	}
	
	public  BigDecimal getBigDecimal(int colIndex ) throws SQLException
	{
	 return this.resultSet.getBigDecimal(colIndex);
	}
	public  BigDecimal getBigDecimal(String colName ) throws SQLException
	{
	 return this.resultSet.getBigDecimal(colName);
	}
	public  InputStream getBinaryStream(String colName ) throws SQLException
	{
	 return this.resultSet.getBinaryStream(colName);
	}
	public  InputStream getBinaryStream(int colIndex ) throws SQLException
	{
	 return this.resultSet.getBinaryStream(colIndex);
	}
	public  Blob getBlob(int colIndex ) throws SQLException
	{
	 return this.resultSet.getBlob(colIndex);
	}
	public  Blob getBlob(String colName ) throws SQLException
	{
	 return this.resultSet.getBlob(colName);
	}
	public  Reader getCharacterStream(String colName ) throws SQLException
	{
	 return this.resultSet.getCharacterStream(colName);
	}
	public  Reader getCharacterStream(int colIndex ) throws SQLException
	{
	 return this.resultSet.getCharacterStream(colIndex);
	}
	public  Clob getClob(String colName ) throws SQLException
	{
	 return this.resultSet.getClob(colName);
	}
	public  Clob getClob(int colIndex ) throws SQLException
	{
	 return this.resultSet.getClob(colIndex);
	}
	public  int getConcurrency() throws SQLException
	{
	 return this.resultSet.getConcurrency();
	}
	public  String getCursorName() throws SQLException
	{
	 return this.resultSet.getCursorName();
	}
	public  int getFetchDirection() throws SQLException
	{
	 return this.resultSet.getFetchDirection();
	}
	public  int getFetchSize() throws SQLException
	{
	 return this.resultSet.getFetchSize();
	}
	public  int getHoldability() throws SQLException
	{
	 return this.resultSet.getHoldability();
	}
	public  ResultSetMetaData getMetaData() throws SQLException
	{
	 return this.resultSet.getMetaData();
	}
	public  Reader getNCharacterStream(int colIndex ) throws SQLException
	{
	 return this.resultSet.getNCharacterStream(colIndex );
	}
	public  Reader getNCharacterStream(String colName ) throws SQLException
	{
	 return this.resultSet.getNCharacterStream(colName );
	}
	public  NClob getNClob(String colName ) throws SQLException
	{
	 return this.resultSet.getNClob(colName);
	}
	public  NClob getNClob(int colIndex ) throws SQLException
	{
	 return this.resultSet.getNClob(colIndex);
	}
	public  String getNString(int colIndex ) throws SQLException
	{
	 return this.resultSet.getNString(colIndex);
	}
	public  String getNString(String colName ) throws SQLException
	{
	 return this.resultSet.getNString(colName);
	}
	public  int getRow() throws SQLException
	{
	 return this.resultSet.getRow();
	}
	public  RowId getRowId(String colName ) throws SQLException
	{
	 return this.resultSet.getRowId(colName);
	}
	public  RowId getRowId(int colIndex ) throws SQLException
	{
	 return this.resultSet.getRowId(colIndex);
	}
	public  SQLXML getSQLXML(String colName ) throws SQLException
	{
	 return this.resultSet.getSQLXML(colName);
	}
	public  SQLXML getSQLXML(int colIndex ) throws SQLException
	{
	 return this.resultSet.getSQLXML(colIndex);
	}
	
	public  Time getTime(int colIndex ,Calendar cal) throws SQLException
	{
	 return this.resultSet.getTime(colIndex,cal);
	}
	public  Time getTime(String colName ,Calendar cal) throws SQLException
	{
	 return this.resultSet.getTime(colName,cal);
	}
	public  Time getTime(int colIndex ) throws SQLException
	{
	 return this.resultSet.getTime(colIndex);
	}
	public  Time getTime(String colName ) throws SQLException
	{
	 return this.resultSet.getTime(colName);
	}
	public  Timestamp getTimestamp(int colIndex ) throws SQLException
	{
	 return this.resultSet.getTimestamp(colIndex);
	}
	public  Timestamp getTimestamp(String colName ) throws SQLException
	{
	 return this.resultSet.getTimestamp(colName);
	}
	public  Timestamp getTimestamp(String colName ,Calendar cal) throws SQLException
	{
	 return this.resultSet.getTimestamp(colName,cal);
	}
	public  Timestamp getTimestamp(int colIndex ,Calendar cal) throws SQLException
	{
	 return this.resultSet.getTimestamp(colIndex,cal);
	}
	
	public  SQLWarning getWarnings() throws SQLException
	{
	 return this.resultSet.getWarnings();
	}
	
	
	
	
	

}
