package org.galilee.dms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.galilee.dms.dao.DataValueDAO;
import org.galilee.dms.model.CoreQuery;
import org.galilee.dms.model.DataValues;
import org.galilee.dms.model.Fishes;
import org.galilee.dms.model.Sites;
import org.galilee.dms.service.DataValueService;
import org.galilee.dms.service.FishService;
import org.galilee.dms.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DataValueServiceImpl implements DataValueService {
	
	public static Logger logger = LoggerFactory
			.getLogger(DataValueServiceImpl.class);

	DataValueDAO dataValueDao;
	@Autowired
	FishService fishService;
	@Autowired
	SiteService siteService;

	public void setDataValueDao(DataValueDAO dataValueDao) {
		this.dataValueDao = dataValueDao;
	}

	@Override
	public boolean add(DataValues value) {

		return this.dataValueDao.insert(value);
	}

	@Override
	public boolean update(DataValues value) {

		return this.dataValueDao.update(value);
	}

	@Override
	public boolean delete(int valueID) {

		DataValues value = this.dataValueDao.selectByID(valueID);
		return this.dataValueDao.delete(value);
	}

	@Override
	public DataValues findByID(int valueID) {

		return this.dataValueDao.selectByID(valueID);
	}

	@Override
	public List<DataValues> findAnnualData(int year) {

		return this.dataValueDao.selectByYear(year);
	}
	
	@Override
	public List<DataValues> findPeriodData(String start, String end) {
		
		logger.info("START:{}, END{}", start, end);
		return this.dataValueDao.searchByPeriod(start, end);
	}

	@Override
	public List<DataValues> findTermData(int year, int term) {
		return this.dataValueDao.searchByTerm(year, term);
	}

	@Override
	// Fish 데이터를 검색할 경우 Fish + DataValue Join View를 가져오는게 좋은가?
	// 왜냐 하면 우점종 종 비율 등은 종 수를 볼때도 있고 종의 개체수를 볼때도 있다
	// 여기서 distinct를 해버리면 추후 연산이 불가능하다. 
	public List<DataValues> searchBySites(int year, List<Integer> sIDList) {
		
		logger.debug("SITEID List:{}", sIDList);
		List<DataValues> dataList = this.dataValueDao.searchAnnualBySite(sIDList, year);
		return dataList;
	}

	@Override
	public List<DataValues> searchByFish(int year, List<Integer> fIDList) {

		logger.debug("FeatureID List:{}",fIDList);
		List<DataValues> dataList = this.dataValueDao.searchAnnualByFish(fIDList, year);
		return dataList;
	}

	@Override
	public List<Integer> searchSurveyYears() {
		
		return this.dataValueDao.searchSurveyYears();
	}
	
	@Override
	public List<DataValues> searchBySites(String start, String end, List<Integer> sIDList) {
		
		logger.debug("SITEID List:{}",sIDList);
		return this.dataValueDao.searchBySite(sIDList, start, end);
	}
	
	/*
	 * Year, Term + SiteID List 로 데이터 검색
	 * @see org.galilee.dms.service.DataValueService#searchBySites(int, int, java.lang.String)
	 */
	@Override
	public List<DataValues> searchBySites(int year, int term, List<Integer> sIDList) {
		
		logger.debug("SITEID List:{}",sIDList);
		return this.dataValueDao.searchTermBySite(sIDList, year, term);
	}


	@Override
	public List<DataValues> searchByFish(int year, int term, List<Integer> fIDList) {
		
		logger.debug("FeatureID List:{}",fIDList);
		return this.dataValueDao.searchTermByFish(fIDList, year, term);
	}


	@Override
	public List<DataValues> searchFishBySites(int year, int term, List<Integer> sIDList) {
		logger.debug("search Term Fish by SiteIDList:{}", sIDList);
		List<DataValues> valueList = new ArrayList<DataValues>();
		List<DataValues> values = this.dataValueDao.searchTermBySite(sIDList, year, term);
		for(DataValues v: values){
			if(v.getFeature().getFeatureType().equals("Fish")){
				valueList.add(v);
			}				
		}
		return valueList;
	}

	@Override
	public List<DataValues> searchByFish(String start, String end,
			List<Integer> fIDList) {
		return this.dataValueDao.searchByFish(fIDList, start, end);
	}

	@Override
	public List<DataValues> search(CoreQuery qr) {
		List<DataValues> values = this.dataValueDao.search(qr.getSiteIDs(), qr.getFeatureIDs(), qr.getVariableIDs(), qr.getStart(), qr.getEnd());
		return values;
	}
}
