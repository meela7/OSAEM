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

import com.naemp.osaem.dao.FishDAO;
import com.naemp.osaem.model.Fish;

public class FishDAOImpl implements FishDAO {
	
	/**
	 * Class Name:	FishDAOImpl.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FishDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public FishDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	@Transactional
	public boolean create(Fish fish) {		
		logger.info("Create a Fish Named {}", fish.getSpecies());
		try {
			sessionFactory.getCurrentSession().save(fish);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public Fish read(int fishID) {
		Fish fish = (Fish) sessionFactory.getCurrentSession().get(Fish.class, fishID);
		return fish;
	}

	@Override
	@Transactional
	public boolean update(Fish fish) {
		try {
			sessionFactory.getCurrentSession().update(fish);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean delete(int fishID) {
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM Fish WHERE FishID = :fishID");
			query.setParameter("fishID", fishID);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public List<Fish> list() {
		@SuppressWarnings("unchecked")
		List<Fish> fishList = (List<Fish>) sessionFactory.getCurrentSession().createCriteria(Fish.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		
		return fishList;
	}

	@Override
	@Transactional
	public Fish getByUniqueKey(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Fish WHERE Species = :species ");
		if (name != null) {
			query.setParameter("species", name);
		}
		Fish fish = (Fish) query.uniqueResult();
		return fish;
	}

	@Override
	@Transactional
	public List<Fish> search(Map<String, String> map) {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "FROM Fish WHERE ";
		
		// get all the columns of the Fish
		// remove the parameters which doesn't match with column in the list
		AbstractEntityPersister aep=((AbstractEntityPersister)sessionFactory.getClassMetadata(Fish.class));  
		String[] properties = aep.getPropertyNames();
		List<String> columnNames = Arrays.asList(properties);
		
		for(String key : map.keySet()){
			if(!columnNames.contains(key) && !key.equals("FishID") )
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
		List<Fish> fishs = (List<Fish>) query.list();
		return fishs;
	}

	@Override
	@Transactional
	public List<Fish> listSearch(Map<String, List<String>> map) {
		
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "FROM Fish WHERE ";
		
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
		List<Fish> fishes = (List<Fish>) query.list();
		
		return fishes;
	}

}
