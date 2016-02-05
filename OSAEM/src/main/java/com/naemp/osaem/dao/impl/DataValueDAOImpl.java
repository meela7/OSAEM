package com.naemp.osaem.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.naemp.osaem.dao.DataValueDAO;
import com.naemp.osaem.model.DataValue;
import com.naemp.osaem.model.Site;

public class DataValueDAOImpl implements DataValueDAO {
	
	/**
	 * Class Name:	DataValueDAOImpl.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private static final Logger logger = LoggerFactory.getLogger(DataValueDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	public DataValueDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	@Transactional
	public boolean create(DataValue value) {
		logger.info("Create a Value from Site: {}", value.getSiteID());
		try {
			sessionFactory.getCurrentSession().save(value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public DataValue read(int dataValueID) {
		DataValue value = (DataValue) sessionFactory.getCurrentSession().get(DataValue.class, dataValueID);
		return value;
	}

	@Override
	@Transactional
	public DataValue getByUniqueKey(String dateTime, int siteID, int variableID, int featureID, int sourceID,
			int methodID) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM DataValue WHERE DateTime = :dateTime and SiteID = :siteID and VariableID = :variableID and FeatureID = :featureID and SourceID = :sourceID and MethodID = :methodID ");
		query.setParameter("dateTime", dateTime);
		query.setParameter("siteID", siteID);
		query.setParameter("variableID", variableID);
		query.setParameter("featureID", featureID);
		query.setParameter("sourceID", sourceID);
		query.setParameter("methodID", methodID);
		
		DataValue value = (DataValue) query.uniqueResult();
		return value;
	}

	@Override
	@Transactional
	public List<DataValue> search(Map<String, String> map) {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "FROM DataValue WHERE ";
		 
		// get all the columns of the Site
		// remove the parameters which doesn't match with column in the list
		AbstractEntityPersister aep=((AbstractEntityPersister)sessionFactory.getClassMetadata(DataValue.class));  
		String[] properties = aep.getPropertyNames();
		List<String> columnNames = Arrays.asList(properties);
		
		Map<String, String> params = new HashMap<String, String>();
		for(String key : map.keySet()){
			logger.info("Parameter: {}, Value: {}", key, map.get(key));
			if(columnNames.contains(key))
				params.put(key, map.get(key));
		}
		
		int index = 0;
		for(String key : params.keySet()){
			if(index == 0 )
				hqlQuery = hqlQuery + key + " = :" + key ;
			else
				hqlQuery = hqlQuery + " and " + key + " = :" + key ;
			index++;
		}
		
		// execute HQL Query
		logger.info("Execute Query: {}", hqlQuery);
		Query query = session.createQuery(hqlQuery);
		for(String key : params.keySet()){
			
			if(key.equals("DateTime") || key.equals("Latitude") || key.equals("Longitude"))
				query.setParameter(key, params.get(key));
			else
				query.setParameter(key, Integer.parseInt(params.get(key)));
		}
		@SuppressWarnings("unchecked")
		List<DataValue> values = (List<DataValue>) query.list();
		return values;
	}

}
