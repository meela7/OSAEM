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

import com.naemp.osaem.dao.VariableDAO;
import com.naemp.osaem.model.Variable;

public class VariableDAOImpl implements VariableDAO {
	
	/**
	 * Class Name:	VariableDAOImpl.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.01
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private static final Logger logger = LoggerFactory.getLogger(VariableDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public VariableDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	@Transactional
	public boolean create(Variable variable) {		
		logger.info("Create a Variable Named {}", variable.getVariableName());
		try {
			sessionFactory.getCurrentSession().save(variable);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public Variable read(int variableID) {
		Variable variable = (Variable) sessionFactory.getCurrentSession().get(Variable.class, variableID);
		return variable;
	}

	@Override
	@Transactional
	public boolean update(Variable variable) {
		try {
			sessionFactory.getCurrentSession().update(variable);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean delete(int variableID) {
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM Variable WHERE VariableID = :variableID");
			query.setParameter("variableID", variableID);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public List<Variable> list() {
		@SuppressWarnings("unchecked")
		List<Variable> listVariable = (List<Variable>) sessionFactory.getCurrentSession().createCriteria(Variable.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		
		return listVariable;
	}

	@Override
	@Transactional
	public Variable getByUniqueKey(String name, int unitID) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Variable WHERE VariableName = :variableName and UnitID = :unitID ");
		query.setParameter("variableName", name);
		query.setParameter("unitID", unitID);
		
		Variable variable = (Variable) query.uniqueResult();
		return variable;
	}

	@Override
	@Transactional
	public List<Variable> search(Map<String, String> map) {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "FROM Variable WHERE ";
		 
		// get all the columns of the Variable
		// remove the parameters which doesn't match with column in the list
		AbstractEntityPersister aep=((AbstractEntityPersister)sessionFactory.getClassMetadata(Variable.class));  
		String[] properties = aep.getPropertyNames();
		List<String> columnNames = Arrays.asList(properties);
		
		for(String key : map.keySet()){
			if(!columnNames.contains(key) && !key.equals("VariableID") && !key.equals("Unit"))
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
			if(key.equals("VariableID") || key.equals("StreamOrder"))
				query.setParameter(key, Integer.parseInt(map.get(key)));
			else
				query.setParameter(key, map.get(key));
		}
		@SuppressWarnings("unchecked")
		List<Variable> variables = (List<Variable>) query.list();
		return variables;
	}

}
