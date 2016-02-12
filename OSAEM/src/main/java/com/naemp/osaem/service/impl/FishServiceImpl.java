package com.naemp.osaem.service.impl;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.dao.FishDAO;
import com.naemp.osaem.model.Fish;
import com.naemp.osaem.service.FishService;

public class FishServiceImpl implements FishService {

	/**
	 * Class Name:	FishServiceImpl.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	FishDAO fishDao;
	
	public void setFishDao(FishDAO fishDao){
		this.fishDao = fishDao;
	}
	@Override
	public int newInstance(Fish fish) {
		int newfishID = 0;
		if(fishDao.create(fish))
			newfishID = fishDao.getByUniqueKey(fish.getSpecies()).getFishID();

		return newfishID;
	}

	@Override
	public Fish readInstance(int fishID) {
		Fish fish = fishDao.read(fishID);
		return fish;
	}

	@Override
	public boolean updateInstance(Fish fish) {
		return fishDao.update(fish);
	}

	@Override
	public boolean deleteInstance(int fishID) {
		return fishDao.delete(fishID);
	}

	@Override
	public List<Fish> readCollection() {
		return fishDao.list();
	}

	@Override
	public boolean isInstanceExist(String name) {
		if(fishDao.getByUniqueKey(name) != null)
			return true;
		return false;
	}

	@Override
	public List<Fish> search(Map<String, String> map) {
		return fishDao.search(map);
	}
	@Override
	public List<Fish> listSearch(Map<String, List<String>> map) {
		
		return fishDao.listSearch(map);
	}

}
