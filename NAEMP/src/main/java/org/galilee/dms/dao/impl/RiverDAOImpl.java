package org.galilee.dms.dao.impl;

import java.util.List;

import org.galilee.dms.dao.RiverDAO;
import org.galilee.dms.model.Rivers;
import org.galilee.dms.model.Sites;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class RiverDAOImpl implements RiverDAO {

private SessionFactory sessionFactory;
	
	public RiverDAOImpl(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Rivers river) {
		sessionFactory.getCurrentSession().save(river);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Rivers river) {
		sessionFactory.getCurrentSession().update(river);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Rivers river) {
		sessionFactory.getCurrentSession().delete(river);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Rivers selectByID(int riverID) {
		Rivers river = (Rivers) sessionFactory.getCurrentSession().get(
				Rivers.class, riverID);
		return river;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Rivers> selectAll() {
		@SuppressWarnings("unchecked")
		List<Rivers> riverList = sessionFactory.getCurrentSession()
				.createCriteria(Rivers.class).
				addOrder(Order.asc("RiverID")).list();
		return riverList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Rivers> selectByIDs(List<Integer> riverIDs) {
		
		return this.sessionFactory.getCurrentSession()
				.createCriteria(Rivers.class)
				.add(Restrictions.in("RiverID", riverIDs))
				.addOrder(Order.asc("RiverName"))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Rivers> selectByName(String riverName) {
		
		return this.sessionFactory.getCurrentSession()
				.createCriteria(Rivers.class)
				.add(Restrictions.eq("RiverName", riverName))
				.addOrder(Order.asc("RiverID")).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Rivers> selectByClassification(String classification) {
		
		return this.sessionFactory.getCurrentSession()
				.createCriteria(Rivers.class)
				.add(Restrictions.eq("Classification", classification))
				.addOrder(Order.asc("RiverName"))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Rivers> selectByBasin(String basin) {
		
		return this.sessionFactory.getCurrentSession()
				.createCriteria(Rivers.class)
				.add(Restrictions.eq("Basin", basin))
				.addOrder(Order.asc("RiverName"))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Rivers> selectByMidWatershed(String mid) {

		return this.sessionFactory.getCurrentSession()
				.createCriteria(Rivers.class)
				.add(Restrictions.eq("MidWatershed", mid))
				.addOrder(Order.asc("RiverName"))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Rivers> selectBySubWatershed(String sub) {

		return this.sessionFactory.getCurrentSession()
				.createCriteria(Rivers.class)
				.add(Restrictions.eq("SubWatershed", sub))
				.addOrder(Order.asc("RiverName"))
				.list();
	}

}
