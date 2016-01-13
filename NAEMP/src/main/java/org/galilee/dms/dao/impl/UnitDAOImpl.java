package org.galilee.dms.dao.impl;

import java.util.List;

import org.galilee.dms.dao.UnitDAO;
import org.galilee.dms.model.Units;
import org.galilee.dms.model.Variables;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class UnitDAOImpl implements UnitDAO{

	private SessionFactory sessionFactory;

	public UnitDAOImpl(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Units insert(Units unit) {
		this.sessionFactory.getCurrentSession().save(unit);
		return unit;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Units update(Units unit) {
		this.sessionFactory.getCurrentSession().update(unit);
		return unit;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Units unit) {
		this.sessionFactory.getCurrentSession().delete(unit);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Units selectByID(int unitID) {
		
		return (Units) this.sessionFactory.getCurrentSession().get(Units.class, unitID);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Units> selectAll() {
		@SuppressWarnings("unchecked")
		List<Units> unitList = this.sessionFactory.getCurrentSession().createCriteria(Units.class)
				.addOrder(Order.asc("UnitID"))
				.list();
		return unitList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Object selectByName(String unitName) {
		@SuppressWarnings("unchecked")
		List<Units> unitList = this.sessionFactory.getCurrentSession().createCriteria(Units.class)
				.add(Restrictions.eq("UnitName", unitName))
				.addOrder(Order.asc("UnitID")).list();
		return unitList;
	}

}
