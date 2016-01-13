package org.galilee.dms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.galilee.dms.dao.RiverDAO;
import org.galilee.dms.model.Rivers;
import org.galilee.dms.service.RiverService;

public class RiverServiceImpl implements RiverService {

	RiverDAO riverDao;

	public void setRiverDao(RiverDAO riverDao) {
		this.riverDao = riverDao;
	}
	
	@Override
	public void add(Rivers river) {
		
		this.riverDao.insert(river);
	}

	@Override
	public void update(Rivers river) {
		
		this.riverDao.update(river);
	}

	@Override
	public void delete(int riverID) {
		
		Rivers river = this.riverDao.selectByID(riverID);
		this.riverDao.delete(river);
	}
	
	@Override
	public Rivers findByID(int riverID) {
		
		return this.riverDao.selectByID(riverID);
	}

	@Override
	public List<Rivers> findAll() {
		
		return this.riverDao.selectAll();
	}

	@Override
	public List<Rivers> findByIDs(List<Integer> riverIDs) {
		
		return this.riverDao.selectByIDs(riverIDs);
	}

	@Override
	public List<Rivers> findByClassification(String classification) {
		
		return this.riverDao.selectByClassification(classification);
	}

	@Override
	public List<Rivers> findByBasin(String basin) {
		
		return this.riverDao.selectByBasin(basin);
	}

	@Override
	public List<Rivers> findByMid(String mid) {
		
		return this.riverDao.selectByMidWatershed(mid);
	}
	
	@Override
	public List<Rivers> findBySub(String sub) {
		
		return this.riverDao.selectBySubWatershed(sub);
	}
	
	@Override
	public List<Rivers> findByBasinNClass(String basin, String classification) {
		
		List<Rivers> riverList = new ArrayList<Rivers>();
		List<Rivers> rivers = this.riverDao.selectByBasin(basin);
		System.out.println(classification);
		for(Rivers river: rivers){
			if(river.getClassification().equals(classification))
				riverList.add(river);
		}
		return riverList;
		
	}

	@Override
	public List<Rivers> findByName(String riverName) {
		
		return this.riverDao.selectByName(riverName);
	}
}
