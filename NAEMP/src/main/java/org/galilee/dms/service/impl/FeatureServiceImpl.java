package org.galilee.dms.service.impl;

import java.util.List;

import org.galilee.dms.dao.FeatureDAO;
import org.galilee.dms.model.Features;
import org.galilee.dms.service.FeatureService;

public class FeatureServiceImpl implements FeatureService {

	FeatureDAO featureDao;
	
	public void setFeatureDao(FeatureDAO featureDao){
		this.featureDao = featureDao;
	}

	@Override
	public boolean add(Features feature) {
		
		return this.featureDao.insert(feature);
	}

	@Override
	public boolean update(Features feature) {
		
		return this.featureDao.update(feature);
	}

	@Override
	public boolean delete(Features feature) {
		
		return this.featureDao.delete(feature);
	}

	@Override
	public Features findByID(int featureID) {	
		
		return this.featureDao.selectByID(featureID);
	}

	@Override
	public List<Features> findAll() {

		return this.featureDao.selectAll();
	}

	@Override
	public List<Features> findByName(String featureName) {
		
		return this.featureDao.selectByName(featureName);
	}	
}
