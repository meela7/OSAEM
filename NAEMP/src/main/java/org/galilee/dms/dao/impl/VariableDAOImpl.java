package org.galilee.dms.dao.impl;

import java.util.List;

import org.galilee.dms.dao.VariableDAO;
import org.galilee.dms.model.VariableNameCV;
import org.galilee.dms.model.Variables;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class VariableDAOImpl implements VariableDAO {

	private SessionFactory sessionFactory;

	public VariableDAOImpl(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Variables insert(Variables variable) {
				
		this.sessionFactory.getCurrentSession().save(variable);
		return variable; 
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Variables update(Variables variable) {
				
		this.sessionFactory.getCurrentSession().update(variable);
		return variable;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Variables variable) {
				
		this.sessionFactory.getCurrentSession().delete(variable);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Variables selectByID(int variableID) {
				
		return (Variables) this.sessionFactory.getCurrentSession().get(Variables.class, variableID);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Variables> selectAll() {
				
		@SuppressWarnings("unchecked")
		List<Variables> variableList = this.sessionFactory.getCurrentSession().createCriteria(Variables.class)
				.addOrder(Order.asc("VariableID")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return variableList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Variables> selectByType(String type) {
		
		@SuppressWarnings("unchecked")
		List<Variables> variableList = this.sessionFactory.getCurrentSession()
			.createCriteria(Variables.class, "variable")
			.setFetchMode("variable.ValueType", FetchMode.JOIN).createAlias("variable.ValueType", "type")
			.add(Restrictions.eq("type.Term", type))
			.addOrder(Order.asc("VariableID")).list();
		return variableList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Variables> selectByName(VariableNameCV variableName) {
		
		@SuppressWarnings("unchecked")
		List<Variables> variableList = this.sessionFactory.getCurrentSession()
			.createCriteria(Variables.class, "variable")
			.setFetchMode("variable.VariableName", FetchMode.JOIN).createAlias("variable.VariableName", "name")
			.add(Restrictions.eq("name.Term", variableName))
			.addOrder(Order.asc("VariableID")).list();
		return variableList;
	}

}
