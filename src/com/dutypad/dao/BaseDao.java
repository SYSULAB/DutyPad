package com.dutypad.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.base.util.Page;

public interface BaseDao<E>{


	/**
	 * findById object
	 * 
	 * @param entityId
	 */
	public E findById(Serializable entityId);
	/**
	 * Persist object
	 * 
	 * @param entity
	 */
	public void persist(E entity);
	
	/**
	 * Remove a persistent instance
	 * 
	 * @param [] id
	 */
	public boolean deleteByPK(Serializable... id);
	/**
	 * Remove a persistent instance
	 * 
	 * @param entity
	 */
	public void delete(E entity);

	/**
	 * delete entity by property though hql
	 * 
	 * @param propName
	 * @param propValue
	 */
	public void deleteByProperties(String propName, Object propValue);

	/**
	 * delete entity by properties though hql
	 * 
	 * @param propName
	 * @param propValue
	 */
	public void deleteByProperties(String[] propName, Object[] propValue);

	/**
	 * Update the persistent instance with the identifier of the given detached instance.
	 * 
	 * @param entity
	 */
	public void update(E entity);

	/**
	 * update property batch
	 * 
	 * @param conditionName where clause condiction property name
	 * @param conditionValue where clause condiction property value
	 * @param propertyName update clause property name array
	 * @param propertyValue update clase property value array
	 */
	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName, Object[] propertyValue);

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String propertyName, Object propertyValue);

	public void updateByProperties(String conditionName, Object conditionValue, String[] propertyName, Object[] propertyValue);

	public void updateByProperties(String conditionName, Object conditionValue, String propertyName, Object propertyValue);

	public void update(E entity, Serializable oldId);
	/**
	 * Merge the state of the given entity into the current persistence context.
	 * 
	 * @param entity
	 */
	public E merge(E entity);


	/**
	 * get an entity by properties
	 * 
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public E getByProperties(String[] propName, Object[] propValue);

	public E getByProperties(String[] propName, Object[] propValue, Map<String, String> sortedCondition);

	/**
	 * get an entity by property
	 * 
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public E getByProperties(String propName, Object propValue);

	public E getByProperties(String propName, Object propValue, Map<String, String> sortedCondition);

	public E get(Serializable id);

	public E load(Serializable id);
	/**
	 * query by property
	 * 
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public List<E> queryByProperties(String[] propName, Object[] propValue,
			Map<String, String> sortedCondition, Integer buttom, Integer top, String[] betweenName,
			Object[] minValue, Object[] maxValue);
	
	public List<E> queryByProperties(String[] propName, Object[] propValue,
			Map<String, String> sortedCondition, String betweenName,
			Object minValue, Object maxValue);
	
	public List<E> queryByProperties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer buttom, Integer top);
	
	public List<E> queryByProperties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer top);

	public List<E> queryByProperties(String[] propName, Object[] propValue, Map<String, String> sortedCondition);

	public List<E> queryByProperties(String[] propName, Object[] propValue, Integer top);

	public List<E> queryByProperties(String[] propName, Object[] propValue);

	public List<E> queryByProperties(String propName, Object propValue, Map<String, String> sortedCondition, Integer top);

	public List<E> queryByProperties(String propName, Object propValue, Map<String, String> sortedCondition);

	public List<E> queryByProperties(String propName, Object propValue, Integer top);

	public List<E> queryByProperties(String propName, Object propValue);

	public List<E> queryByPage(String propName, Object propValue, Map<String, String> sortedCondition,  Page page);
	
	public List<E> queryByPage(String[] propName, Object[] propValue, Map<String, String> sortedCondition,  Page page);
	
	public List<E> queryByPage(String propName, Object propValue, Page page);
	
	public List<E> queryByPage(String[] propName, Object[] propValue, Page page);
	
	public List<E> queryAll(Map<String, String> sortedCondition);
	
	public List<E> queryAll();
	
	public List<E> queryAllByPage(Page page);
	/**
	 * Completely clear the session.
	 */
	public void clear();

	/**
	 * Remove this instance from the session cache.
	 */
	public void evict(E entity);

	/**
	 * count all
	 * 
	 * @return
	 */
	public int countAll();
	/*
	 * 15-12-12
	 */
	public int countTotalPage(String query_str, Map<String, Object> params);
	public List<E> getObjectsByQuery(String query_str, Map<String, Object> params);
	public List<E> getPageObjectsByQuery(String query_str, Map<String, Object> params, Page page);

}
