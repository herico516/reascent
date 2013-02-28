package com.reascent.framework.base.dao;

import java.util.List;

import com.reascent.framework.exception.DAOException;

public interface InterfaceDAO 
{
   /**
    * get one record by primary key.
    */
	public <T> T find(String sql,String primaryKey ) throws DAOException;
	/**
	 * get one record by multiple keys
	 */
	public <T> T find(String sql,Object[] primaryKey ) throws DAOException;
	
	/**
	 * query records by condition 
	 */
	public <T> List<T> list(String sql,String param ) throws DAOException;
	/**
	 * query records by conditions 
	 */
	public <T> List<T> list(String sql,Object[] params ) throws DAOException;
	
	/**
	 * delete one record by primary key
	 */
	public boolean delete(String sql,String primaryKey ) throws DAOException;
	public <T> boolean delete(String sql,T t ) throws DAOException;
	/**
	 * delete one record by multiple primary key
	 */
	public boolean delete(String sql,Object[] primaryKey ) throws DAOException;
	/**
	 * delete records by condition
	 */
	public int deleteBatch(String sql,Object[] params ) throws DAOException;
	public <T>int deleteBatch(String sql,List<T> t ) throws DAOException;
	
	
	/**
	 * update one record by primary Key
	 */
	public <T> boolean update(String sql,T t ) throws DAOException;
	/**
	 * update one record by primary Key
	 */
	public int update(String sql,String primaryKey,Object[] params ) throws DAOException;
	/**
	 * update one record by multiple primary Key
	 */
	public int update(String sql,Object[] primaryKey,Object[] params ) throws DAOException;
	
	/**
	 * update records
	 */
	public int updateBacth(String sql,Object[] params ) throws DAOException;
	public <T> int updateBacth(String sql,List<T> t ) throws DAOException;
	
	/**
	 * check if the record exist with the primary key
	 */
	public <T> boolean exist(String sql, T t) throws DAOException;
	/**
	 * check if the record exist with the primary key
	 */
	public boolean exist(String sql,String primaryKey) throws DAOException;
	/**
	 * check if the record exist with the multiple primary key
	 */
	public boolean exist(String sql,Object[] primaryKey) throws DAOException;
	
	
	
	public int count(String sql) throws DAOException;
	
	public int count(String sql,Object[] params) throws DAOException;
	
	/**
	 * add one record
	 */
	public <T> boolean add(String sql, T t) throws DAOException;
	public  boolean add(String sql, String primaryKey,Object[] params) throws DAOException;
	public  boolean add(String sql, Object[] primaryKey,Object[] params) throws DAOException;
	/**
	 * add records
	 */
	public <T> int addBatch(String sql,List<T> t) throws DAOException;
	
	
	
	
	
}
