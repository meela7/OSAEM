package org.galilee.dms.dao;

import java.util.List;

import org.galilee.dms.model.Rivers;

public interface RiverDAO {
	
	public void insert(Rivers river);
	public void update(Rivers river);
	public void delete(Rivers river);
	public Rivers selectByID(int riverID);
	
	public List<Rivers> selectAll();
	public List<Rivers> selectByIDs(List<Integer> riverIDs);
	public List<Rivers> selectByClassification(String classification);
	public List<Rivers> selectByBasin(String basin);
	public List<Rivers> selectByMidWatershed(String mid);
	public List<Rivers> selectBySubWatershed(String sub);
	public List<Rivers> selectByName(String riverName);
}
