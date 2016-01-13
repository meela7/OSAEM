package org.galilee.dms.dao.impl;

import java.util.List;

import org.galilee.dms.dao.MethodDAO;
import org.galilee.dms.model.Methods;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class MethodDAOImpl implements MethodDAO {

	SessionFactory sessionFactory;
	public MethodDAOImpl(SessionFactory sf){
		this.sessionFactory = sf;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean insert(Methods method) {
		
		try{
			this.sessionFactory.getCurrentSession().save(method);
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean update(Methods method) {
		
		try{
			this.sessionFactory.getCurrentSession().update(method);
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean delete(Methods method) {
		
		try{
			this.sessionFactory.getCurrentSession().delete(method);
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Methods selectByID(int methodID) {
		
		return (Methods) this.sessionFactory.getCurrentSession().get(Methods.class, methodID);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Methods> selectAll() {
		
		return this.sessionFactory.getCurrentSession().createCriteria(Methods.class).addOrder(Order.asc("MethodID")).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Methods> selectByName(String methodName) {
		return this.sessionFactory.getCurrentSession()
				.createCriteria(Methods.class)
				.add(Restrictions.eq("MethodName", methodName))
				.addOrder(Order.asc("MethodName"))
				.list();
	}

}
