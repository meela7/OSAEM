package org.galilee.dms.service;

import java.util.List;

import org.galilee.dms.model.CoreQuery;
import org.galilee.dms.model.DataValues;

public interface DataValueService {	
	
	public boolean add(DataValues value);
	public boolean update(DataValues value);	
	public boolean delete(int valueID);
	public DataValues findByID(int valueID);
	public List<DataValues> findAnnualData(int year);
	public List<DataValues> findPeriodData(String start, String end);
	public List<DataValues> findTermData(int year, int term);
	
	public List<Integer> searchSurveyYears();
	
	public List<DataValues> searchBySites(int year, List<Integer> sIDList);
	public List<DataValues> searchBySites(int year, int term, List<Integer> sIDList);
	public List<DataValues> searchBySites(String start, String end, List<Integer> sIDList);
	
	public List<DataValues> searchByFish(int year, List<Integer> fIDList);	
	public List<DataValues> searchByFish(int year, int term, List<Integer> fIDList);
	public List<DataValues> searchByFish(String start, String end, List<Integer> fIDList);
	
	public List<DataValues> searchFishBySites(int year, int term, List<Integer> sIDList);
	
	public List<DataValues> search(CoreQuery qr);
}
