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
		 * Criteria�� �̿��� ���. Criteria�� �ڹ� Ŭ������ �̿��ؼ� �پ��� �˻� ������ ����� �� �ֵ��� �� �ִ�
		 * API�̴�. Join�� �ϴ��� ���� ������ Criteria�� ���� Object�� Return Pros: User
		 * defined Criteria. Cons: It costs to convert from Criteria to SQL
		 * statement.
		 */

		@SuppressWarnings("unchecked")
		List<River> listRiver = (List<River>) sessionFactory.getCurrentSession().createCriteria(River.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
				/*
				 * HQL�� �̿��� ���. HQL�� SQL�� ����� ������� �����͸� ��ȸ�� �� �ֵ��� ���ִ� Hibernate��
				 * �����ϴ� ���� ����̴�. Join�� �� ��� �迭�� �����Ͽ� ��� Return Pros: high
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
		if (name != null) {
			query.setParameter("riverName", name);
		}
		if (mid != null) {
			query.setParameter("midWatershed", mid);
		}
		if (sub != null) {
			query.setParameter("subWatershed", sub);
		}
		River river = (River) query.uniqueResult();
		return river;
	}

	@Override
	@Transactional
	public List<River> search(Map<String, String> map) {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "FROM River WHERE ";
		
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
//			logger.info("Parameter: {}, Value: {}", key, map.get(key));
			if(index == 0 )
				hqlQuery = hqlQuery + key + " = :" + key ;
			else
				hqlQuery = hqlQuery + " and " + key + " = :" + key ;
			index++;
		}
		
		// execute HQL Query
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
}
