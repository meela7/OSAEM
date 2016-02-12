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

import com.naemp.osaem.dao.SourceDAO;
import com.naemp.osaem.model.Source;

public class SourceDAOImpl implements SourceDAO {
	
	/**
	 * Class Name:	SourceDAOImpl.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private static final Logger logger = LoggerFactory.getLogger(SourceDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public SourceDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	@Transactional
	public boolean create(Source source) {		
		logger.info("Create a Source Named {}", source.getInstitution());
		try {
			sessionFactory.getCurrentSession().save(source);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public Source read(int sourceID) {
		Source source = (Source) sessionFactory.getCurrentSession().get(Source.class, sourceID);
		return source;
	}

	@Override
	@Transactional
	public boolean update(Source source) {
		try {
			sessionFactory.getCurrentSession().update(source);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean delete(int sourceID) {
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM Source WHERE SourceID = :sourceID");
			query.setParameter("sourceID", sourceID);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public List<Source> list() {
		@SuppressWarnings("unchecked")
		List<Source> sourceList = (List<Source>) sessionFactory.getCurrentSession().createCriteria(Source.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		
		return sourceList;
	}

	@Override
	@Transactional
	public Source getByUniqueKey(String institution, String investigator) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Source WHERE Institution = :institution and Investigator = :investigator ");
		query.setParameter("institution", institution);
		query.setParameter("investigator", investigator);
		
		Source source = (Source) query.uniqueResult();
		return source;
	}

	@Override
	@Transactional
	public List<Source> search(Map<String, String> map) {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "FROM Source WHERE ";
		
		// get all the columns of the Source
		// remove the parameters which doesn't match with column in the list
		AbstractEntityPersister aep=((AbstractEntityPersister)sessionFactory.getClassMetadata(Source.class));  
		String[] properties = aep.getPropertyNames();
		List<String> columnNames = Arrays.asList(properties);
		
		for(String key : map.keySet()){
			if(!columnNames.contains(key) && !key.equals("SourceID"))
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
		List<Source> sources = (List<Source>) query.list();
		return sources;
	}

	@Override
	@Transactional
	public List<Source> listSearch(Map<String, List<String>> map) {
		
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "FROM Source WHERE ";

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
		List<Source> sources = (List<Source>) query.list();
		
		return sources;
	}
}
