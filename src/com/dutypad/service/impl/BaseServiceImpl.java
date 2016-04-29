package com.dutypad.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.dutypad.dao.BaseDao;
import com.dutypad.service.BaseService;

public class BaseServiceImpl<E> implements BaseService<E>{
	protected BaseDao<E> dao;
	public E findById(Serializable entityId) {
		return dao.findById(entityId);
	}
	
	public void persist(E entity) {
		dao.persist(entity);
	}
	
	public boolean deleteByPK(Serializable... id) {
		return dao.deleteByPK(id);
	}

	public void delete(E entity) {
		dao.delete(entity);
	}

	public void deleteByProperties(String[] propName, Object[] propValue) {
		dao.deleteByProperties(propName, propValue);
	}

	public void deleteByProperties(String propName, Object propValue) {
		dao.deleteByProperties(propName, propValue);
	}

	public E getByProperties(String[] propName, Object[] propValue) {
		return dao.getByProperties(propName, propValue);
	}

	public E getByProperties(String propName, Object propValue) {
		return dao.getByProperties(propName, propValue);
	}

	public E getByProperties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		return dao.getByProperties(propName, propValue, sortedCondition);
	}

	public E getByProperties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return dao.getByProperties(propName, propValue, sortedCondition);
	}

	public List<E> queryByProperties(String[] propName, Object[] propValue) {
		return dao.queryByProperties(propName, propValue);
	}

	public List<E> queryByProperties(String propName, Object propValue) {
		return dao.queryByProperties(propName, propValue);
	}

	public List<E> queryByProperties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		return dao.queryByProperties(propName, propValue, sortedCondition);
	}

	public List<E> queryByProperties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return dao.queryByProperties(propName, propValue, sortedCondition);
	}

	public List<E> queryByProperties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer top) {
		return dao.queryByProperties(propName, propValue, sortedCondition, top);
	}

	public List<E> queryByProperties(String[] propName, Object[] propValue, Integer top) {
		return dao.queryByProperties(propName, propValue, top);
	}

	public List<E> queryByProperties(String propName, Object propValue, Map<String, String> sortedCondition, Integer top) {
		return dao.queryByProperties(propName, propValue, sortedCondition, top);
	}

	public List<E> queryByProperties(String propName, Object propValue, Integer top) {
		return dao.queryByProperties(propName, propValue, top);
	}
	
	public List<E> queryByPage(String propName, Object propValue,
			Map<String, String> sortedCondition, Page page) {
		return dao.queryByPage(propName, propValue, sortedCondition, page);
	}
	
	public List<E> queryByPage(String propName, Object propValue, Page page) {
		return dao.queryByPage(propName, propValue, page);
	}

	public List<E> queryByPage(String[] propName, Object[] propValue,
			Map<String, String> sortedCondition, Page page) {
		return dao.queryByPage(propName, propValue, sortedCondition, page);
	}
	
	public List<E> queryByPage(String[] propName, Object[] propValue, Page page) {
		return dao.queryByPage(propName, propValue, page);
	}
	
	public List<E> queryAll() {
		return dao.queryAll();
	}
	
	public List<E> queryAll(Map<String, String> sortedCondition) {
		return dao.queryAll(sortedCondition);
	}
	
	public List<E> queryAllByPage(Page page) {
		return dao.queryAllByPage(page);
	}
	
	public E merge(E entity) {
		return dao.merge(entity);
	}
	
	public void update(E entity, Serializable oldId) {
		dao.update(entity, oldId);
	}

	public void update(E entity) {
		dao.update(entity);
	}

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName, Object[] propertyValue) {
		dao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	public void updateByProperties(String conditionName, Object conditionValue, String[] propertyName, Object[] propertyValue) {
		dao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String propertyName, Object propertyValue) {
		dao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	public void updateByProperties(String conditionName, Object conditionValue, String propertyName, Object propertyValue) {
		dao.updateByProperties(conditionName, conditionValue, propertyName, propertyValue);
	}

	public void evict(E entity) {
		dao.evict(entity);
	}

	public void clear() {
		dao.clear();
	}

	public int countAll() {
		return dao.countAll();
	}
	
	public E get(Serializable id) {
		return dao.get(id);
	}

	public E load(Serializable id) {
		return dao.load(id);
	}

	public List<E> getObjectsByQuery(String query_str, Map<String, Object> params) {
		return dao.getObjectsByQuery(query_str, params);
	}
	public List<E> getPageObjectsByQuery(String query_str, Map<String, Object> params, Page page){
		return dao.getPageObjectsByQuery(query_str, params, page);
	}
	public int countTotalPage(String query_str, Map<String, Object> params) {
		return dao.countTotalPage(query_str, params);
	}

	@Override
	public List<E> queryByProperties(String[] propName, Object[] propValue,
			Map<String, String> sortedCondition, Integer buttom, Integer top,
			String[] betweenName, Object[] minValue, Object[] maxValue) {
		return dao.queryByProperties(propName, propValue, sortedCondition,buttom, top,betweenName,minValue,maxValue);
	}

	@Override
	public List<E> queryByProperties(String[] propName, Object[] propValue,
			Map<String, String> sortedCondition, String betweenName,
			Object minValue, Object maxValue) {
		return dao.queryByProperties(propName, propValue, sortedCondition, betweenName, minValue, maxValue);
	}




}
