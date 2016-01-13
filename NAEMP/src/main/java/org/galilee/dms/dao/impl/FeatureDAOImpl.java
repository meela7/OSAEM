package org.galilee.dms.dao.impl;

import java.util.List;

import org.galilee.dms.dao.FeatureDAO;
import org.galilee.dms.model.Features;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class FeatureDAOImpl implements FeatureDAO {

	private SessionFactory sessionFactory;

	public FeatureDAOImpl(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean insert(Features feature) {

		try {
			this.sessionFactory.getCurrentSession().save(feature);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean update(Features feature) {

		try {
			this.sessionFactory.getCurrentSession().update(feature);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean delete(Features feature) {

		try {
			this.sessionFactory.getCurrentSession().delete(feature);

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Features selectByID(int featureID) {

		Features feature = (Features) this.sessionFactory.getCurrentSession()
				.get(Features.class, featureID);
		return feature;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Features> selectAll() {

		@SuppressWarnings("unchecked")
		List<Features> featureList = this.sessionFactory.getCurrentSession()
				.createCriteria(Features.class)
				.addOrder(Order.asc("FeatureID"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return featureList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Features> selectByName(String featureName) {
		@SuppressWarnings("unchecked")
		List<Features> featureList = this.sessionFactory.getCurrentSession()
				.createCriteria(Features.class)
				.add(Restrictions.eq("FeatureName", featureName))
				.addOrder(Order.asc("RiverName"))
				.list();
		return featureList;
	}

}
