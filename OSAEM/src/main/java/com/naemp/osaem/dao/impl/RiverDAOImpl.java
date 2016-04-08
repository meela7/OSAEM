package com.naemp.osaem.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.naemp.osaem.dao.RiverDAO;
import com.naemp.osaem.model.River;

public class RiverDAOImpl implements RiverDAO {

	/**
	 * Class Name: RiverDAOImpl.java 
	 * Description:
	 * 
	 * @author Meilan Jiang
	 * @since 2016.01.29
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private static final Logger logger = LoggerFactory.getLogger(RiverDAOImpl.class);

	private SessionFactory sessionFactory;

	public RiverDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public boolean create(River river) {
		try {
			sessionFactory.getCurrentSession().save(river);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public River read(int riverID) {
		River river = (River) sessionFactory.getCurrentSession().get(River.class, riverID);
		return river;
	}

	@Override
	@Transactional
	public boolean update(River river) {
		try {
			sessionFactory.getCurrentSession().update(river);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean delete(int riverID) {

		// ---------- delete by Object
		// try {
		// sessionFactory.getCurrentSession().delete(river);
		// } catch (Exception e) {
		// e.printStackTrace();
		// return false;
		// }
		// return true;

		// ----------- delete by ID
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM River WHERE RiverID = :riverID");
			query.setParameter("riverID", riverID);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public List<River> list() {

		/*
		 * Criteria를 이용한 방법. Criteria는 자바 클래스를 이용해서 다양한 검색 조건을 명시할 수 있도록 해 주는
		 * API이다. Join을 하더라도 최초 생성한 Criteria에 대한 Object만 Return Pros: User
		 * defined Criteria. Cons: It costs to convert from Criteria to SQL
		 * statement.
		 */

		@SuppressWarnings("unchecked")
		List<River> listRiver = (List<River>) sessionFactory.getCurrentSession().createCriteria(River.class)
				.addOrder(Order.asc("RiverID")).list();
				/*
				 * HQL을 이용한 방법. HQL은 SQL과 비슷한 방법으로 데이터를 조회할 수 있도록 해주는 Hibernate가
				 * 제공하는 쿼리 언어이다. Join을 할 경우 배열에 저장하여 모두 Return Pros: high
				 * performance with caching. Cons: It costs to convert from HQL
				 * to SQL statement.
				 */

		// Session session = sessionFactory.getCurrentSession();
		// Query query = session.createQuery("from Rivers");
		// listRiver = query.list();

		return listRiver;
	}

	@Override
	@Transactional
	public River getByUniqueKey(String name, String mid, String sub) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"FROM River WHERE RiverName = :riverName and MidWatershed = :midWatershed and SubWatershed = :subWatershed ");
		query.setParameter("riverName", name);
		query.setParameter("midWatershed", mid);
		query.setParameter("subWatershed", sub);
		
		River river = (River) query.uniqueResult();
		return river;
	}

	@Override
	@Transactional
	public List<River> search(Map<String, String> map) {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "FROM River WHERE ";
		
		// 이것이 필요한지 확인 필요함. Map을 controller나 service에서 Site Object로 바꿔주면 된다.
		// get all the columns of the River
		// remove the parameters which doesn't match with column in the list
		AbstractEntityPersister aep=((AbstractEntityPersister)sessionFactory.getClassMetadata(River.class));  
		String[] properties = aep.getPropertyNames();
		List<String> columnNames = Arrays.asList(properties);
		
		for(String key : map.keySet()){
			if(!columnNames.contains(key) && !key.equals("RiverID"))
				map.remove(key);
		}
		
		// create HQL Statement
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
			if(key.equals("RiverID"))
				query.setParameter(key, Integer.parseInt(map.get(key)));
			else
				query.setParameter(key, map.get(key));
		}
		@SuppressWarnings("unchecked")
		List<River> rivers = (List<River>) query.list();
		
		return rivers;
	}

	@Override
	@Transactional
	public List<River> listSearch(Map<String, List<String>> map) {
		
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "FROM River WHERE ";
		
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
		List<River> rivers = (List<River>) query.list();
		
		return rivers;
	}
}
