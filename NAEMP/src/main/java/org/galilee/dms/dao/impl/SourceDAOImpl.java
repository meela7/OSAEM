package org.galilee.dms.dao.impl;

import java.util.List;

import org.galilee.dms.dao.SourceDAO;
import org.galilee.dms.model.Sources;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class SourceDAOImpl implements SourceDAO {

	private SessionFactory sessionFactory;

	public SourceDAOImpl(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Sources insert(Sources source) {
		sessionFactory.getCurrentSession().save(source);
		return source;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Sources update(Sources source) {
		this.sessionFactory.getCurrentSession().update(source);
		return source;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Sources source) {
		sessionFactory.getCurrentSession().delete(source);;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Sources selectByID(int sourceID) {
		Sources source = (Sources) sessionFactory.getCurrentSession().get(
				Sources.class, sourceID);
		return source;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Sources> selectAll() {
		
		@SuppressWarnings("unchecked")
		List<Sources> sourceList = sessionFactory.getCurrentSession()
				.createCriteria(Sources.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return sourceList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Sources> selectByInstitute(String institute) {
		
		@SuppressWarnings("unchecked")
		List<Sources> sourceList = sessionFactory.getCurrentSession()
				.createCriteria(Sources.class)
				.add(Restrictions.like("Institution", "%"+institute+"%"))
				.addOrder(Order.asc("SourceID")).list();
		return sourceList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Sources> selectByContact(String contact) {
		
		@SuppressWarnings("unchecked")
		List<Sources> sourceList = sessionFactory.getCurrentSession()
				.createCriteria(Sources.class)
				.add(Restrictions.like("Investigator", "%"+contact))
				.addOrder(Order.asc("SourceID")).list();
		return sourceList;
	}

}
