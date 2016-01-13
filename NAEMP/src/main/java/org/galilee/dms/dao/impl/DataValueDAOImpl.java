package org.galilee.dms.dao.impl;

import java.util.List;

import org.galilee.dms.dao.DataValueDAO;
import org.galilee.dms.model.DataValues;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class DataValueDAOImpl implements DataValueDAO {

	SessionFactory sessionFactory;
	public DataValueDAOImpl(SessionFactory sf){
		this.sessionFactory = sf;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean insert(DataValues value) {

		try{
			this.sessionFactory.getCurrentSession().save(value);
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean update(DataValues value) {

		try{
			this.sessionFactory.getCurrentSession().update(value);
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean delete(DataValues value) {
		try{
			this.sessionFactory.getCurrentSession().delete(value);
		}catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
		return true;
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public DataValues selectByID(int valueID) {

		return (DataValues) this.sessionFactory.getCurrentSession().get(DataValues.class, valueID);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DataValues> selectByYear(int year) {

//		Session session = this.sessionFactory.getCurrentSession();
//		Query query = session.createQuery("from DataValues where SurveyYear = :year ");
//		query.setParameter("year", year);
//		List<DataValues> dataList = query.list();
		return this.sessionFactory.getCurrentSession().createCriteria(DataValues.class)
				.add(Restrictions.eq("SurveyYear", year))
				.list();
	}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRED)
//	public List<Fishes> searchFishBySite(int year, List<Integer> sites) {
//		
//		Session session = this.sessionFactory.getCurrentSession();
//		Query query = session.createQuery("select distint Feature.FeatureID from DataValues where SurveyYear = :year and SiteID in :sites ");
//		query.setParameter("year", year);
//		query.setParameter("sites", sites);
//		@SuppressWarnings("unchecked")
//		List<Fishes> fishes = query.list();
////		List<Integer> fIDs = this.sessionFactory.getCurrentSession()
////				.createCriteria(DataValues.class).add(Restrictions.in("SiteID", sites))
////				.add(Restrictions.eq("SurveyYear", year))
////				.setProjection(Projections.distinct(Projections.property("FeatureID")))
////				.addOrder(Order.asc("FeatureID")).setFetchMode("Feature", FetchMode.JOIN).list();
//		return fishes;
//	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DataValues> searchAnnualByFish(List<Integer> fIDList, int year) {
				
		return this.sessionFactory.getCurrentSession().createCriteria(DataValues.class, "value")
				.setFetchMode("value.Feature", FetchMode.JOIN).createAlias("value.Feature", "feature")
				.add(Restrictions.eq("SurveyYear", year))
				.add(Restrictions.in("feature.FeatureID", fIDList))
				.addOrder(Order.desc("DataValue"))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DataValues> searchAnnualBySite(List<Integer> sIDList, int year) {
				
		return this.sessionFactory.getCurrentSession().createCriteria(DataValues.class, "value")
				.setFetchMode("value.Feature", FetchMode.JOIN).createAlias("value.Site", "site")
				.add(Restrictions.eq("SurveyYear", year))
				.add(Restrictions.in("site.SiteID", sIDList))
				.addOrder(Order.desc("DataValue"))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DataValues> searchByPeriod(String start, String end) {
		return this.sessionFactory.getCurrentSession().createCriteria(DataValues.class, "value")
				.add(Restrictions.between("DateTime", start, end))
				.addOrder(Order.desc("DataValue"))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DataValues> searchByTerm(int year, int term) {
		return this.sessionFactory.getCurrentSession().createCriteria(DataValues.class, "value")
				.add(Restrictions.eq("SurveyYear", year))
				.add(Restrictions.eq("SurveyTerm", term))
				.addOrder(Order.desc("DataValue"))
				.list();
	}
	
/*
 * 여기서부터는 DataValue 조건부 검색
 * (non-Javadoc)
 * @see org.galilee.dms.dao.DataValueDAO#searchSurveyYears()
 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Integer> searchSurveyYears() {
//		Session session = this.sessionFactory.getCurrentSession();
//		Query query = session.createQuery("select distinct SurveyYear from DataValues WHERE SurveyYear is not null");

		return this.sessionFactory.getCurrentSession().createCriteria(DataValues.class, "value")
				.add(Restrictions.isNotNull("SurveyYear"))
				.addOrder(Order.asc("SurveyYear"))
				.setProjection( Projections.distinct( Projections.property("SurveyYear") ) )
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DataValues> searchTermBySite(List<Integer> sIDList,
			int year, int term) {
		return this.sessionFactory.getCurrentSession().createCriteria(DataValues.class, "value")
				.setFetchMode("value.Site", FetchMode.JOIN).createAlias("value.Site", "site")
				.add(Restrictions.eq("SurveyYear", year))
				.add(Restrictions.eq("SurveyTerm", term))
				.add(Restrictions.in("site.SiteID", sIDList))
				.addOrder(Order.desc("DataValue"))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DataValues> searchTermByFish(List<Integer> fIDList,
			int year, int term) {
		
		return this.sessionFactory.getCurrentSession().createCriteria(DataValues.class, "value")
				.setFetchMode("value.Feature", FetchMode.JOIN).createAlias("value.Feature", "feature")
				.add(Restrictions.eq("SurveyYear", year))
				.add(Restrictions.eq("SurveyTerm", term))
				.add(Restrictions.in("feature.FeatureID", fIDList))
				.addOrder(Order.desc("DataValue"))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DataValues> searchBySite(List<Integer> sIDList, String start, String end) {
		return this.sessionFactory.getCurrentSession().createCriteria(DataValues.class, "value")
				.setFetchMode("value.Site", FetchMode.JOIN).createAlias("value.Site", "site")
				.add(Restrictions.between("DateTime", start, end))
				.add(Restrictions.in("site.SiteID", sIDList))
				.addOrder(Order.desc("DataValue"))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DataValues> searchByFish(List<Integer> fIDList, String start,
			String end) {
		return this.sessionFactory.getCurrentSession().createCriteria(DataValues.class, "value")
				.setFetchMode("value.Feature", FetchMode.JOIN).createAlias("value.Feature", "feature")
				.add(Restrictions.between("DateTime", start, end))
				.add(Restrictions.in("feature.FeatureID", fIDList))
				.addOrder(Order.desc("DataValue"))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DataValues> search(List<Integer> siteIDs,
			List<Integer> featureIDs, List<Integer> variableIDs,
			String start, String end) {
		
		return this.sessionFactory.getCurrentSession().createCriteria(DataValues.class, "value")
				.setFetchMode("value.Site", FetchMode.JOIN).createAlias("value.Site", "site")
				.setFetchMode("value.Feature", FetchMode.JOIN).createAlias("value.Feature", "feature")
				.setFetchMode("value.Variable", FetchMode.JOIN).createAlias("value.Variable", "variable")
				.add(Restrictions.between("DateTime", start, end))
				.add(Restrictions.in("site.SiteID", siteIDs))
				.add(Restrictions.in("feature.FeatureID", featureIDs))
				.add(Restrictions.in("variable.VariableID", variableIDs))
				.addOrder(Order.asc("DateTime"))
				.list();
	}
}
