package com.naemp.osaem.dao.impl;

import java.util.ArrayList;
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

import com.naemp.osaem.dao.SiteDAO;
import com.naemp.osaem.model.Site;

public class SiteDAOImpl implements SiteDAO {
	
	/**
	 * Class Name:	SiteDAOImpl.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.01
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private static final Logger logger = LoggerFactory.getLogger(SiteDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public SiteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	@Transactional
	public boolean create(Site site) {		
		logger.info("Create a Site Named {}", site.getSiteName());
		try {
			sessionFactory.getCurrentSession().save(site);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public Site read(int siteID) {
		Site site = (Site) sessionFactory.getCurrentSession().get(Site.class, siteID);
		return site;
	}

	@Override
	@Transactional
	public boolean update(Site site) {
		try {
			sessionFactory.getCurrentSession().update(site);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean delete(int siteID) {
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM Site WHERE SiteID = :siteID");
			query.setParameter("siteID", siteID);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public List<Site> list() {
		@SuppressWarnings("unchecked")
		List<Site> listSite = (List<Site>) sessionFactory.getCurrentSession().createCriteria(Site.class)
				.addOrder(Order.asc("SiteID")).list();
		
		return listSite;
	}

	@Override
	@Transactional
	public Site getByUniqueKey(String name, String lat, String lng) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Site WHERE SiteName = :siteName and Latitude = :latitude and Longitude = :longitude ");
		query.setParameter("siteName", name);
		query.setParameter("latitude", lat);
		query.setParameter("longitude", lng);
		
		Site site = (Site) query.uniqueResult();
		return site;
	}

	@Override
	@Transactional
	public List<Site> search(Map<String, String> map) {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "FROM Site WHERE ";
		 
		// get all the columns of the Site
		// remove the parameters which doesn't match with column in the list
		AbstractEntityPersister aep=((AbstractEntityPersister)sessionFactory.getClassMetadata(Site.class));  
		String[] properties = aep.getPropertyNames();
		List<String> columnNames = Arrays.asList(properties);
		
		for(String key : map.keySet()){
			if(!columnNames.contains(key) && !key.equals("SiteID") && !key.equals("River"))
				map.remove(key);
		}
		
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
			if(key.equals("SiteID") || key.equals("StreamOrder"))
				query.setParameter(key, Integer.parseInt(map.get(key)));
			else
				query.setParameter(key, map.get(key));
		}
		@SuppressWarnings("unchecked")
		List<Site> sites = (List<Site>) query.list();
		return sites;
	}

	@Override
	@Transactional
	public List<Site> listSearch(Map<String, List<String>> map) {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "FROM Site WHERE ";
		
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
			if(key.equals("StreamOrder")){
				List<Integer> values = new ArrayList<Integer>();
				for(String value: map.get(key)){
					values.add(Integer.parseInt(value));
				}
				query.setParameterList(key, values);
			}
			else
				query.setParameterList(key, map.get(key));
		}
		@SuppressWarnings("unchecked")
		List<Site> sites = (List<Site>) query.list();
		return sites;
	}

//	@Override
//	@Transactional
//	public List<Site> getByIDs(List<Integer> siteIDs) {
//		Session session = sessionFactory.getCurrentSession();
//		Query query = session.createQuery("FROM Site WHERE SiteID in :siteIDList ");
//		query.setParameterList("siteIDList", siteIDs);
//		
//		List<Site> sites = (List<Site>) query.list();
//		return sites;
//	}
}
