package org.galilee.dms.dao.impl;

import java.util.List;

import org.galilee.dms.dao.SiteCodeDAO;
import org.galilee.dms.model.SiteCode;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class SiteCodeDAOImpl implements SiteCodeDAO {

	SessionFactory sessionFactory;
	public SiteCodeDAOImpl (SessionFactory sf){
		this.sessionFactory = sf;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean insert(SiteCode siteCode) {
		try{
			this.sessionFactory.getCurrentSession().save(siteCode);
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean update(SiteCode siteCode) {
		try{
			this.sessionFactory.getCurrentSession().update(siteCode);
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean delete(SiteCode siteCode) {
		try{
			this.sessionFactory.getCurrentSession().delete(siteCode);
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public SiteCode selectByID(int siteCodeID) {
		
		return (SiteCode) this.sessionFactory.getCurrentSession().get(SiteCode.class, siteCodeID);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SiteCode> selectAll() {
		return this.sessionFactory.getCurrentSession().createCriteria(SiteCode.class).addOrder(Order.asc("SiteCode")).list();
	}

}
