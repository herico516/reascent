package com.reascent.framework.base.dao;

import java.util.List;

import javax.sql.DataSource;

import com.reascent.framework.db.helper.DBConstant;
import com.reascent.framework.db.helper.DBUtils;
import com.reascent.framework.exception.DAOException;

public class AbstractJDBCDAO implements InterfaceDAO {
	
    private DBUtils dbUtils;
    
    private String schema = DBConstant.DEFAULT_SCHEMA ;
    
    private boolean needCache = false ;
	
	public void setDataSource(DataSource dataSource) 
	{
		this.dbUtils = new DBUtils(dataSource,schema,needCache);
	}
	

	
	protected String getSchema() {
		return schema;
	}



	protected void setSchema(String schema) 
	{
		this.schema = schema;
		if(dbUtils!=null)
		{
			dbUtils.setSchema(schema);
		}
	}



	protected boolean isNeedCache() {
		return needCache;
	}



	protected void setNeedCache(boolean needCache)
	{
		this.needCache = needCache;
		
		if(dbUtils!=null)
		{
			dbUtils.setNeedCache(this.needCache);
		}
	}



	@Override
	public <T> boolean add(String sql, T t) throws DAOException {
		
		return false;
	}

	@Override
	public boolean add(String sql, String primaryKey, Object[] params)
			throws DAOException {
		
		return false;
	}

	@Override
	public boolean add(String sql, Object[] primaryKey, Object[] params)
			throws DAOException {
		
		return false;
	}

	@Override
	public <T> int addBatch(String sql, List<T> t) throws DAOException {
		
		return 0;
	}

	@Override
	public int count(String sql) throws DAOException {
		
		return 0;
	}

	@Override
	public int count(String sql, Object[] params) throws DAOException {
		
		return 0;
	}

	@Override
	public boolean delete(String sql, String primaryKey) throws DAOException {
		
		return false;
	}

	@Override
	public <T> boolean delete(String sql, T t) throws DAOException {
		
		return false;
	}

	@Override
	public boolean delete(String sql, Object[] primaryKey) throws DAOException {
		
		return false;
	}

	@Override
	public int deleteBatch(String sql, Object[] params) throws DAOException {
		
		return 0;
	}

	@Override
	public <T> int deleteBatch(String sql, List<T> t) throws DAOException {
		
		return 0;
	}

	@Override
	public <T> boolean exist(String sql, T t) throws DAOException {
		
		return false;
	}

	@Override
	public boolean exist(String sql, String primaryKey) throws DAOException {
		
		return false;
	}

	@Override
	public boolean exist(String sql, Object[] primaryKey) throws DAOException {
		
		return false;
	}

	@Override
	public <T> T find(String sql, String primaryKey) throws DAOException {
		
		return null;
	}

	@Override
	public <T> T find(String sql, Object[] primaryKey) throws DAOException {
		
		return null;
	}

	@Override
	public <T> List<T> list(String sql, String param) throws DAOException {
		
		return null;
	}

	@Override
	public <T> List<T> list(String sql, Object[] params) throws DAOException {
		
		return null;
	}

	@Override
	public <T> boolean update(String sql, T t) throws DAOException {
		
		return false;
	}

	@Override
	public int update(String sql, String primaryKey, Object[] params)
			throws DAOException {
		
		return 0;
	}

	@Override
	public int update(String sql, Object[] primaryKey, Object[] params)
			throws DAOException {
		
		return 0;
	}

	@Override
	public int updateBacth(String sql, Object[] params) throws DAOException {
		
		return 0;
	}

	@Override
	public <T> int updateBacth(String sql, List<T> t) throws DAOException {
		
		return 0;
	}
	
	

}
