package org.galilee.dms.service;

import java.util.List;

import org.galilee.dms.model.Features;

public interface FeatureService {	
	
	public boolean add(Features feature);
	public boolean update(Features feature);	
	public boolean delete(Features feature);
	public Features findByID(int featureID);
	public List<Features> findAll();
	public List<Features> findByName(String featureName);
	
}
