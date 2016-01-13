package org.galilee.dms.dao;

import java.util.List;

import org.galilee.dms.model.DataValues;

public interface DataValueDAO {
	
	public boolean insert(DataValues value);
	public boolean update(DataValues value);
	public boolean delete(DataValues value);
	public DataValues selectByID(int valueID);
	
	public List<DataValues> searchByPeriod(String start, String end);
	public List<DataValues> searchByTerm(int year, int term);
	public List<DataValues> selectByYear(int year);
//	public List<Fishes> searchFishBySite(int year, List<Integer> sites);
	
	public List<DataValues> searchAnnualBySite(List<Integer> sIDList, int year);
	public List<DataValues> searchAnnualByFish(List<Integer> fIDList, int year);
	
	public List<Integer> searchSurveyYears();
	
	public List<DataValues> searchTermBySite(List<Integer> sIDList,
			int year, int term);
	public List<DataValues> searchTermByFish(List<Integer> fIDList,
			int year, int term);
	
	public List<DataValues> searchBySite(List<Integer> sIDList, String start,
			String end);	
	public List<DataValues> searchByFish(List<Integer> fIDList, String start,
			String end);
	public List<DataValues> search(List<Integer> siteIDs,
			List<Integer> featureIDs, List<Integer> variableIDs,
			String start, String end);
	
}
