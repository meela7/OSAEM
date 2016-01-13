package org.galilee.dms.dao;

import java.util.List;

import org.galilee.dms.model.Features;

public interface FeatureDAO {
	
	public boolean insert(Features feature);
	public boolean update(Features feature);
	public boolean delete(Features feature);
	public Features selectByID(int featureID);
	public List<Features> selectAll();
	public List<Features> selectByName(String featureName);
	
}
