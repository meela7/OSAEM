package com.naemp.osaem.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.naemp.osaem.dao.UnitDAO;
import com.naemp.osaem.model.Unit;

public class UnitDAOImpl implements UnitDAO {
	
	/**
	 * Class Name:	UnitDAOImpl.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UnitDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public UnitDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	@Transactional
	public boolean create(Unit unit) {		
		logger.info("Create a Unit Named {}", unit.getUnitName());
		try {
			sessionFactory.getCurrentSession().save(unit);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public Unit read(int unitID) {
		Unit unit = (Unit) sessionFactory.getCurrentSession().get(Unit.class, unitID);
		return unit;
	}

	@Override
	@Transactional
	public boolean update(Unit unit) {
		try {
			sessionFactory.getCurrentSession().update(unit);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean delete(int unitID) {
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM Unit WHERE UnitID = :unitID");
			query.setParameter("unitID", unitID);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public List<Unit> list() {
		@SuppressWarnings("unchecked")
		List<Unit> unitList = (List<Unit>) sessionFactory.getCurrentSession().createCriteria(Unit.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		
		return unitList;
	}

	@Override
	@Transactional
	public Unit getByUniqueKey(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Unit WHERE UnitName = :unitName ");
		if (name != null) {
			query.setParameter("unitName", name);
		}
		Unit unit = (Unit) query.uniqueResult();
		return unit;
	}

	@Override
	@Transactional
	public List<Unit> search(Map<String, String> map) {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "FROM Unit WHERE ";
		
		// get all the columns of the Unit
		// remove the parameters which doesn't match with column in the list
		AbstractEntityPersister aep=((AbstractEntityPersister)sessionFactory.getClassMetadata(Unit.class));  
		String[] properties = aep.getPropertyNames();
		List<String> columnNames = Arrays.asList(properties);
		
		for(String key : map.keySet()){
			if(!columnNames.contains(key) && !key.equals("UnitID"))
				map.remove(key);
		}
		
		// get parameters from map and Uppercase the first letter
		int index = 0;
		for(String key : map.keySet()){
			if(index == 0 )
				hqlQuery = hqlQuery + key + " = :" + key ;
			else
				hqlQuery = hqlQuery + " and " + key + " = :" + key ;
			index++;
		}
		
		// execute HQL Query
		logger.info("Execute Query: {}", hqlQuery);
		Query query = session.createQuery(hqlQuery);
		for(String key : map.keySet()){
			query.setParameter(key, map.get(key));
		}
		@SuppressWarnings("unchecked")
		List<Unit> units = (List<Unit>) query.list();
		return units;
	}

	@Override
	@Transactional
	public List<Unit> listSearch(Map<String, List<String>> map) {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "FROM Unit WHERE ";
		
		// get all the columns of the River
		// remove the parameters which doesn't match with column in the list
//		AbstractEntityPersister aep=((AbstractEntityPersister)sessionFactory.getClassMetadata(River.class));  
//		String[] properties = aep.getPropertyNames();
//		List<String> columnNames = Arrays.asList(properties);
//		
//		for(String key : map.keySet()){
//			if(!columnNames.contains(key))
//				map.remove(key);
//		}
		
		// create HQL Statement
		int index = 0;
		for(String key : map.keySet()){
			
			if(index == 0 )
				hqlQuery = hqlQuery + key + " in :" + key ;
			else
				hqlQuery = hqlQuery + " and " + key + " in :" + key ;
			index++;
		}
		
		// execute HQL Query
		logger.info("Execute Query: {}", hqlQuery);
		Query query = session.createQuery(hqlQuery);
		for(String key : map.keySet()){
			query.setParameterList(key, map.get(key));
		}
		@SuppressWarnings("unchecked")
		List<Unit> units = (List<Unit>) query.list();
		
		return units;
	}

}
