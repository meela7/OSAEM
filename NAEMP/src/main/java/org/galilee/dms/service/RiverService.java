package org.galilee.dms.service;

import java.util.List;

import org.galilee.dms.model.Rivers;

public interface RiverService {	
	
	public void add(Rivers river);	
	public void update(Rivers river);	
	public void delete(int riverID);
	public Rivers findByID(int riverID);
	
	public List<Rivers> findAll();
	public List<Rivers> findByIDs(List<Integer> riverIDs);
	
	public List<Rivers> findByClassification(String classification);
	public List<Rivers> findByBasin(String basin);
	public List<Rivers> findByMid(String mid);
	public List<Rivers> findBySub(String sub);
	public List<Rivers> findByBasinNClass(String basin, String classification);
	public List<Rivers> findByName(String riverName);
}
