package com.dutypad.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.enterprise.inject.New;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.DataException;

import com.base.util.Page;
import com.dutypad.dao.BaseDao;


public class BaseDaoImpl<E> implements BaseDao<E>{
	
	@Resource
	private SessionFactory sessionFactory;
	
	protected final Logger log = Logger.getLogger(BaseDaoImpl.class);

	private static Map<String, Method> MAP_METHOD = new HashMap<String, Method>();
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public E findById(Serializable entityId) {
		return (E)getSession().get(entityClass, entityId);
	};

	protected Class<E> entityClass;

/*	public BaseDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}*/
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.entityClass = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		//System.out.println(t);
		if (t instanceof ParameterizedType) {
		    Type[] p = ((ParameterizedType) t).getActualTypeArguments();
		    this.entityClass = (Class<E>) p[0];
		}
	}
	
	public void persist(E entity)  throws DataException {
		getSession().save(entity);
	}

	public boolean deleteByPK(Serializable... id) {
		boolean result = false;
		if (id != null && id.length > 0) {
			for (int i = 0; i < id.length; i++) {
				E entity = get(id[i]);
				if (entity != null) {
					getSession().delete(entity);
					result = true;
				}
			}
		}
		return result;
	}

	public void deleteByProperties(String[] propName, Object[] propValue) {
		if (propName != null && propName.length > 0 && propValue != null && propValue.length > 0 && propValue.length == propName.length) {
			StringBuffer sb = new StringBuffer("delete from " + entityClass.getSimpleName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			Query query = getSession().createQuery(sb.toString());
			setParameter(query, propName, propValue);
			query.executeUpdate();
		}
	}

	public void delete(E entity) {
		getSession().delete(entity);
	}

	public void deleteByProperties(String propName, Object propValue) {
		deleteByProperties(new String[] { propName }, new Object[] { propValue });
	}

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String[] propertyName, Object[] propertyValue) throws DataException {
		if (propertyName != null && propertyName.length > 0 && propertyValue != null && propertyValue.length > 0 && propertyName.length == propertyValue.length && conditionValue != null && conditionValue.length > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("update " + entityClass.getSimpleName() + " o set ");
			for (int i = 0; i < propertyName.length; i++) {
				sb.append(propertyName[i] + " = :p_" + propertyName[i] + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(" where 1=1 ");
			appendQL(sb, conditionName, conditionValue);
			Query query = getSession().createQuery(sb.toString());
			for (int i = 0; i < propertyName.length; i++) {
				query.setParameter("p_" + propertyName[i], propertyValue[i]);
			}
			setParameter(query, conditionName, conditionValue);
			query.executeUpdate();
		} else {
			throw new IllegalArgumentException("Method updateByProperties in BaseDao argument is illegal!");
		}
	}

	public void updateByProperties(String[] conditionName, Object[] conditionValue, String propertyName, Object propertyValue) {
		updateByProperties(conditionName, conditionValue, new String[] { propertyName }, new Object[] { propertyValue });
	}

	public void updateByProperties(String conditionName, Object conditionValue, String[] propertyName, Object[] propertyValue) {
		updateByProperties(new String[] { conditionName }, new Object[] { conditionValue }, propertyName, propertyValue);
	}

	public void updateByProperties(String conditionName, Object conditionValue, String propertyName, Object propertyValue) {
		updateByProperties(new String[] { conditionName }, new Object[] { conditionValue }, new String[] { propertyName }, new Object[] { propertyValue });
	}

	public void update(E entity) {
		getSession().update(entity);
	}
	
	public void update(E entity, Serializable oldId) {
		deleteByPK(oldId);
		persist(entity);
	}

	@SuppressWarnings("unchecked")
	public E merge(E entity) {
		return (E) getSession().merge(entity);
	}
	
	@SuppressWarnings("unchecked")
	public E getByProperties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		if (propName != null && propName.length > 0 && propValue != null && propValue.length > 0 && propValue.length == propName.length) {
			StringBuffer sb = new StringBuffer("select o from " + entityClass.getSimpleName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			if (sortedCondition != null && sortedCondition.size() > 0) {
				sb.append(" order by ");
				for (Entry<String, String> e : sortedCondition.entrySet()) {
					sb.append(e.getKey() + " " + e.getValue() + ",");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			Query query = getSession().createQuery(sb.toString());
			setParameter(query, propName, propValue);
			List<E> list = query.list();
			if (list != null && list.size() > 0)
				return list.get(0);
		}
		return null;
	}


	public E getByProperties(String[] propName, Object[] propValue) {
		return getByProperties(propName, propValue, null);
	}

	public E getByProperties(String propName, Object propValue) {
		return getByProperties(new String[] { propName }, new Object[] { propValue });
	}

	public E getByProperties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return getByProperties(new String[] { propName }, new Object[] { propValue }, sortedCondition);
	}

	@SuppressWarnings("unchecked")
	public List<E> queryByProperties(String[] propName, Object[] propValue, 
			Map<String, String> sortedCondition, Integer buttom, Integer top) {
		if (propName != null && propValue != null && propValue.length == propName.length) {
			StringBuffer sb = new StringBuffer("select o from " + entityClass.getSimpleName() + " o where 1=1 ");
			appendQL(sb, propName, propValue);
			if (sortedCondition != null && sortedCondition.size() > 0) {
				sb.append(" order by ");
				for (Entry<String, String> e : sortedCondition.entrySet()) {
					sb.append(e.getKey() + " " + e.getValue() + ",");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			System.out.println(sb.toString() + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			Query query = getSession().createQuery(sb.toString());
			setParameter(query, propName, propValue);
			if(buttom != null) query.setFirstResult(buttom);
			if (top != null) query.setMaxResults(top);
			return query.list();
		}
		return null;
	}

	public List<E> queryAllByPage(Page page) {
		StringBuffer sb = new StringBuffer("select o from " + entityClass.getSimpleName() + " o where 1=1 ");
		Query query = getSession().createQuery(sb.toString());
		query.setFirstResult(page.getBeginItem());
		query.setMaxResults(page.getItemsPerPage());
		return query.list();
	}
	public List<E> queryByProperties(String[] propName, Object[] propValue, Map<String, String> sortedCondition, Integer top) {
		return queryByProperties(propName, propValue, sortedCondition, null, top);
	}
	
	public List<E> queryByProperties(String[] propName, Object[] propValue, Integer top) {
		return queryByProperties(propName, propValue, null, null, top);
	}

	public List<E> queryByProperties(String[] propName, Object[] propValue, Map<String, String> sortedCondition) {
		return queryByProperties(propName, propValue, sortedCondition, null, null);
	}

	public List<E> queryByProperties(String propName, Object propValue, Map<String, String> sortedCondition, Integer top) {
		return queryByProperties(new String[] { propName }, new Object[] { propValue }, sortedCondition, null, top);
	}

	public List<E> queryByProperties(String propName, Object propValue, Map<String, String> sortedCondition) {
		return queryByProperties(new String[] { propName }, new Object[] { propValue }, sortedCondition, null, null);
	}

	public List<E> queryByProperties(String propName, Object propValue, Integer top) {
		return queryByProperties(new String[] { propName }, new Object[] { propValue }, null, null, top);
	}

	public List<E> queryByProperties(String[] propName, Object[] propValue) {
		return queryByProperties(propName, propValue, null, null, null);
	}

	public List<E> queryByProperties(String propName, Object propValue) {
		return queryByProperties(new String[] { propName }, new Object[] { propValue }, null, null, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<E> queryAll(Map<String, String> sortedCondition) {
		return getSession().createQuery("from " + entityClass.getSimpleName() +" o " + buildOrderby(sortedCondition)).list();
	}
	
	public List<E> queryAll() {
		return queryAll(null);
	}
	
	
	public List<E> queryByPage(String propName, Object propValue, Map<String, String> sortedCondition,  Page page) {
		return queryByProperties(new String[] { propName }, new Object[] { propValue },sortedCondition, 
						(page.getCurrentPage()-1)*page.getItemsPerPage(), page.getItemsPerPage());
	}
	
	public List<E> queryByPage(String[] propName, Object[] propValue, Map<String, String> sortedCondition,  Page page) {
		return queryByProperties(propName, propValue, sortedCondition, 
						(page.getCurrentPage()-1)*page.getItemsPerPage(), page.getItemsPerPage());
	}
	
	public List<E> queryByPage(String[] propName, Object[] propValue, Page page) {
		return queryByProperties(propName, propValue, null, page.getBeginItem(), page.getItemsPerPage());
	}
	
	public List<E> queryByPage(String propName, Object propValue, Page page) {
		return queryByProperties(new String[] { propName }, new Object[] { propValue }, null, 
						page.getBeginItem(), page.getItemsPerPage());
	}

	public int countAll() {
		return ((Long)getSession().createQuery("select count(*) from " + entityClass.getSimpleName()).uniqueResult()).intValue();
	}

	public void clear() {
		getSession().clear();
	}

	public void evict(E entity) {
		getSession().evict(entity);
	}

	private void appendQL(StringBuffer sb, String[] propName, Object[] propValue) {
		for (int i = 0; i < propName.length; i++) {
			String name = propName[i];
			Object value = propValue[i];
			if (value instanceof Object[] || value instanceof Collection<?>) {
				Object[] arraySerializable = (Object[]) value;
				if (arraySerializable != null && arraySerializable.length > 0) {
					sb.append(" and o." + name + " in (:" + name.replace(".", "") + ")");
				}
			} else {
				if (value == null) {
					sb.append(" and o." + name + " is null ");
				} else {
					sb.append(" and o." + name + "=:" + name.replace(".", ""));
				}
			}
		}
	}

	private void setParameter(Query query, String[] propName, Object[] propValue) {
		for (int i = 0; i < propName.length; i++) {
			String name = propName[i];
			Object value = propValue[i];
			if (value != null) {
				if (value instanceof Object[]) {
					query.setParameterList(name.replace(".", ""), (Object[]) value);
				} else if (value instanceof Collection<?>) {
					query.setParameterList(name.replace(".", ""), (Collection<?>) value);
				} else {
					query.setParameter(name.replace(".", ""), value);
				}
			}
		}
	}


	@SuppressWarnings("unused")
	private String transferColumn(String queryCondition) {
		return queryCondition.substring(queryCondition.indexOf('_', 1) + 1);
	}

	protected void setParameter(Map<String, Object> mapParameter, Query query) {
		for (Iterator<String> it = mapParameter.keySet().iterator(); it.hasNext();) {
			String parameterName = (String) it.next();
			Object value = mapParameter.get(parameterName);
			query.setParameter(parameterName, value);
		}
	}

	
	@SuppressWarnings("unused")
	private String getOpt(String value) {
		return (value.substring(0, value.indexOf('_', 1))).substring(1);
	}

	@SuppressWarnings("unused")
	private String getPropName(String value) {
		return value.substring(value.indexOf('_', 1) + 1);
	}

	
	/**
	 * 组装order by子句
	 * 
	 * @param orderby
	 *	由属性与asc/desc构成的Map，其中key为属性，value为asc/desc
	 * @return order by 子句
	 */
	protected String buildOrderby(Map<String, String> orderby) {
		StringBuilder orderbyql = new StringBuilder("");
		if (orderby != null && orderby.size() > 0) {
			orderbyql.append(" order by ");
			for (String key : orderby.keySet()) {
				orderbyql.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length() - 1);
		}
		return orderbyql.toString();
	}


	@SuppressWarnings("unchecked")
	public E get(Serializable id) {
		return (E) getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public E load(Serializable id) {
		return (E) getSession().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> getObjectsByQuery(String query_str, Map<String, Object> params) {
		return getPageObjectsByQuery(query_str, params, null);
	}
	
	public List<E> getPageObjectsByQuery(String query_str, Map<String, Object> params, Page page) {
		String hql = "FROM " + (entityClass.getSimpleName()) + " AS "
				+ entityClass.getSimpleName().substring(0, 1) + " WHERE 1=1 "
				+ query_str;
		Query query = getSession().createQuery(hql);
		if(params != null) query.setProperties(params);
		if(page != null){
			query.setFirstResult(page.getBeginItem());
			query.setMaxResults(page.getItemsPerPage());
		}
		return query.list();
	}

	@Override
	public int countTotalPage(String query_str, Map<String, Object> params) {
		String hql = "select count(*) from " + (entityClass.getSimpleName()) + " AS "
				+ entityClass.getSimpleName().substring(0, 1) + " WHERE 1=1 "
				+ query_str;
		Query query =  getSession().createQuery(hql);
		query.setProperties(params);
		return  ((Long) query.uniqueResult()).intValue();
	}

	@Override
	public List<E> queryByProperties(String[] propName, Object[] propValue,
			Map<String, String> sortedCondition, Integer buttom, Integer top,
			String[] betweenName, Object[] minValue, Object[] maxValue) {
		Query query = getQuery(propName, propValue, sortedCondition, buttom, top, betweenName,
				minValue, maxValue);
		return query == null ? null : query.list();
	}


	private Query getQuery(String[] propName, Object[] propValue,
			Map<String, String> sortedCondition, Integer buttom, Integer top, String[] betweenName,
			Object[] minValue, Object[] maxValue) {
		StringBuffer sb = new StringBuffer("select o from " + entityClass.getSimpleName()
				+ " o where 1=1 ");
			if (propName != null && propValue != null && propValue.length == propName.length) {
	
				appendQL(sb, propName, propValue);
				
			}
			
			if (betweenName != null && minValue != null &&maxValue != null) {
				appendBetweenQl(sb, betweenName, minValue, maxValue);
			}
			if (sortedCondition != null && sortedCondition.size() > 0) {
				sb.append(" order by ");
				for (Entry<String, String> e : sortedCondition.entrySet()) {
					sb.append(e.getKey() + " " + e.getValue() + ",");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			Query query = null;
			try {
				query = getSession().createQuery(sb.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (propName != null && propValue != null && propValue.length == propName.length) {
				setParameter(query, propName, propValue);
			}
			
			if (betweenName != null && (minValue != null && maxValue != null)) {
				setBetweenParameter(query, betweenName, minValue, maxValue);
			}
			if (buttom != null)
				query.setFirstResult(buttom);
			if (top != null)
				query.setMaxResults(top);
			return query;
		
	}
	
	
	private void appendBetweenQl(StringBuffer sb, String[] propName, Object[] minValue,
			Object[] maxValue) {
		for (int i = 0; i < propName.length; i++) {
			String name = propName[i];
			sb.append(" and o." + name + " between " + ":min" + name.replace(".", "") + " and :max"
					+ name.replace(".", ""));
		}
	}
	
	private void setBetweenParameter(Query query, String[] propName, Object[] minValue,
			Object[] maxValue) {
		for (int i = 0; i < propName.length; i++) {
			String name = propName[i];
			Object min = minValue[i];
			Object max = maxValue[i];
			if (min != null&&max != null ) {
				query.setParameter("min" + name.replace(".", ""), min);
				query.setParameter("max" + name.replace(".", ""), max);
			}
		}
	}

	@Override
	public List<E> queryByProperties(String[] propName, Object[] propValue,
			Map<String, String> sortedCondition,
			String betweenName, Object minValue, Object maxValue) {
		return queryByProperties(propName,propValue,sortedCondition,null,null,new String[]{betweenName},new Object[]{minValue},new Object[]{maxValue});
	}

}
