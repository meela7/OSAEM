package org.galilee.dms.dao.impl;

import java.util.List;

import org.galilee.dms.dao.FishDAO;
import org.galilee.dms.model.Fishes;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class FishDAOImpl implements FishDAO {
	
	private SessionFactory sessionFactory;

	public FishDAOImpl(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Fishes insert(Fishes fish) {
		
		this.sessionFactory.getCurrentSession().save(fish);
		return fish;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Fishes update(Fishes fish) {
		this.sessionFactory.getCurrentSession().update(fish);
		return fish;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Fishes fish) {
		this.sessionFactory.getCurrentSession().delete(fish);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Fishes selectByID(int featureID) {
		
		Fishes fish = (Fishes) this.sessionFactory.getCurrentSession().get(Fishes.class, featureID);
		return fish;
	}

	@Override	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Fishes> selectAll() {
		
		@SuppressWarnings("unchecked")
		List<Fishes> fishList = this.sessionFactory.getCurrentSession()
				.createCriteria(Fishes.class).addOrder(Order.asc("FishID")).list();
		return fishList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Fishes> selectByIDs(List<Integer> fishIDList) {

		@SuppressWarnings("unchecked")
		List<Fishes> fishList = this.sessionFactory.getCurrentSession()
				.createCriteria(Fishes.class)
				.add(Restrictions.in("FishID", fishIDList))
				.addOrder(Order.asc("FishID")).list();
		return fishList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Fishes> selectEndangered() {
		
		@SuppressWarnings("unchecked")
		List<Fishes> fishList = this.sessionFactory.getCurrentSession()
				.createCriteria(Fishes.class)
				.add(Restrictions.like("EndangeredSpecies", "멸종위기%"))
				.addOrder(Order.asc("FishID")).list();
		return fishList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Fishes> selectByClass(String taxon) {
		
		@SuppressWarnings("unchecked")
		List<Fishes> fishList = this.sessionFactory.getCurrentSession()
				.createCriteria(Fishes.class)
				.add(Restrictions.like("FishClass", taxon))
				.addOrder(Order.asc("FishID")).list();
		return fishList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Fishes> selectByOrder(String taxon) {
		
		@SuppressWarnings("unchecked")
		List<Fishes> fishList = this.sessionFactory.getCurrentSession()
				.createCriteria(Fishes.class)
				.add(Restrictions.like("Order", taxon))
				.addOrder(Order.asc("FishID")).list();
		return fishList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Fishes> selectByFamily(String taxon) {
		
		@SuppressWarnings("unchecked")
		List<Fishes> fishList = this.sessionFactory.getCurrentSession()
				.createCriteria(Fishes.class)
				.add(Restrictions.like("Family", taxon))
				.addOrder(Order.asc("FishID")).list();
		return fishList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Fishes> selectByToleranceGuild(String query) {
		
		@SuppressWarnings("unchecked")
		List<Fishes> fishList = this.sessionFactory.getCurrentSession()
				.createCriteria(Fishes.class, "fish")
				.setFetchMode("fish.ToleranceGuild", FetchMode.JOIN).createAlias("fish.ToleranceGuild", "tolerance")
				.add(Restrictions.like("tolerance.Term", query))
				.addOrder(Order.asc("FishID")).list();
		return fishList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Fishes> selectByTrophicGuild(String query) {
		
		@SuppressWarnings("unchecked")
		List<Fishes> fishList = this.sessionFactory.getCurrentSession()
				.createCriteria(Fishes.class, "fish")
				.setFetchMode("fish.TrophicGuild", FetchMode.JOIN).createAlias("fish.TrophicGuild", "trophic")
				.add(Restrictions.like("trophic.Term", query))
				.addOrder(Order.asc("FishID")).list();
		return fishList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Fishes> selectByHabitatGuild(String query) {
		
		@SuppressWarnings("unchecked")
		List<Fishes> fishList = this.sessionFactory.getCurrentSession()
				.createCriteria(Fishes.class, "fish")
				.setFetchMode("fish.HabitatGuild", FetchMode.JOIN).createAlias("fish.HabitatGuild", "habitat")
				.add(Restrictions.like("habitat.Term", query))
				.addOrder(Order.asc("FishID")).list();
		return fishList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Fishes> selectNative() {
		
		@SuppressWarnings("unchecked")
		List<Fishes> fishList = this.sessionFactory.getCurrentSession()
				.createCriteria(Fishes.class)
				.add(Restrictions.ne("InvasiveSpecies", "Y"))
				.addOrder(Order.asc("FishID")).list();
		return fishList;	
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Fishes> selectBySpecies(String species) {
		
		@SuppressWarnings("unchecked")
		List<Fishes> fishList = this.sessionFactory.getCurrentSession()
			.createCriteria(Fishes.class)
			.add(Restrictions.eq("Species", species))
			.addOrder(Order.asc("FishID")).list();
		return fishList;	
	}
}
