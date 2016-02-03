package org.galilee.dms.controller;

import java.util.List;

import org.galilee.dms.model.CoreQuery;
import org.galilee.dms.model.DataValues;
import org.galilee.dms.service.DataValueService;
import org.galilee.dms.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class DataValueController {
	private static Logger logger = LoggerFactory
			.getLogger(DataValueController.class);

	@Autowired
	private DataValueService dataValueService;

	@Autowired
	private SiteService siteService;

	/*
	 * Services - basic observation web resource
	 */
	//--------------------Create a Observation--------------------
	@RequestMapping(value = "/values/new", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody DataValues value) {

		logger.debug("Creating Observation: {} ..." );					
		this.dataValueService.add(value);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	//--------------------Read a Observation--------------------
	@RequestMapping(value = "/values/{id}", method = RequestMethod.GET)
	public ResponseEntity<DataValues> find(@PathVariable("id") int id) {

		logger.debug("Fetching Observation with ID: {} ...", id);
		DataValues value = this.dataValueService.findByID(id);
		if(value == null){
			logger.debug("Observation with ID: {}, not found.", id );
			return new ResponseEntity<DataValues>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<DataValues>(value, HttpStatus.CREATED);
	}
	
	//--------------------Update a Observation--------------------
	@RequestMapping(value = "/values/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody DataValues value, @PathVariable("id") int valueID) {

		logger.debug("Updating Observation from Site : {} ...", value.getSite().getSiteName());		
		if(valueID != value.getDataValue()){
			logger.debug("Observation ID: {} , {} doesn't match.", valueID, value.getDataValue());
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}else {
			DataValues oldValue = this.dataValueService.findByID(valueID);
			if(oldValue == null){
				logger.debug("Observation with ID: {}, not found.", valueID);
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		}
		this.dataValueService.update(value);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	//--------------------Delete a Observation--------------------
	@RequestMapping(value = "/values/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") int valueID) {

		logger.debug("Fetching & Deleting Observation with ID {} ...", valueID);
		DataValues value = this.dataValueService.findByID(valueID);
		if(value == null){
			logger.debug("Unable to delete. Observation with id: {}, not found.", valueID);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		this.dataValueService.delete(valueID);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/*
	 * observation queries annual search, period search annual search restricted
	 * by sites, period search restricted by sites
	 */

	@RequestMapping(value = "/values", params = { "start", "end" }, method = RequestMethod.GET)
	public List<DataValues> PeriodSearch(@RequestParam("start") String start,
			@RequestParam("end") String end) {

		logger.debug("findByPeriod(start, end) proces has been called.");
		logger.info("START:{}, END:{}", start, end);
		return this.dataValueService.findPeriodData(start, end);

	}

	@RequestMapping(value = "/values", params = { "year", "term" }, method = RequestMethod.GET)
	public List<DataValues> AnnualSearch(@RequestParam("year") int year,
			@RequestParam("term") int term) {

		logger.debug("findByTerm(year, term) proces has been called.");
		return this.dataValueService.findTermData(year, term);
	}

	@RequestMapping(value = "/sites/{id}/values", params = { "start",
			"end" }, method = RequestMethod.GET)
	public List<DataValues> PeriodSearchBySites(
			@PathVariable("id") List<Integer> sIDList,
			@RequestParam("start") String start, @RequestParam("end") String end) {

		logger.info("PeriodSearchBySites(start, end, sIDList) proces has been called.");
		logger.info(sIDList + ", " + start + ", " + end);
		return this.dataValueService.searchBySites(start, end, sIDList);

	}

	@RequestMapping(value = "/sites/{id}/values", params = { "year",
			"term" }, method = RequestMethod.GET)
	public List<DataValues> AnnualSearchBySites(
			@PathVariable("id") List<Integer> sIDList,
			@RequestParam("year") int year, @RequestParam("term") int term) {

		logger.info("AnnualSearchBySites(year, term, sIDList) proces has been called.");
		logger.info(sIDList + ", " + Integer.toString(year) + ", "
				+ Integer.toString(term));
		return this.dataValueService.searchBySites(year, term, sIDList);
	}

	@RequestMapping(value = "/fishes/{id}/values", params = { "start",
			"end" }, method = RequestMethod.GET)
	public List<DataValues> PeriodSearchByFishes(
			@PathVariable("id") List<Integer> fIDList,
			@RequestParam("start") String start, @RequestParam("end") String end) {

		logger.debug("searchByFish(start, end,fIDList) proces has been called.");

		return this.dataValueService.searchByFish(start, end, fIDList);

	}

	@RequestMapping(value = "/fishes/{id}/values", params = { "year",
			"term" }, method = RequestMethod.GET)
	public ResponseEntity<List<DataValues>> AnnualSearchByFishes(
			@PathVariable("id") List<Integer> fIDList,
			@RequestParam("year") int year, @RequestParam("term") int term) {

		logger.debug("searchByFish(start, end,fIDList) proces has been called.");

		List<DataValues> values = this.dataValueService.searchByFish(year, term, fIDList);		
		return new ResponseEntity<List<DataValues>>(values, HttpStatus.OK);		
	}

	// ---------------------- Search-----------------------------------
	@RequestMapping(value = "/observations", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DataValues>> searchByCoreQuery(
			@RequestBody CoreQuery query) {

		logger.info("Searching observation dataset for query: {}", query.toString());
		List<DataValues> values = this.dataValueService.search(query);	
		return new ResponseEntity<List<DataValues>>(values, HttpStatus.OK);

	}

	/*
	 * parameters in the path Angular JS calls API in this version
	 */
	@RequestMapping(value = "/values/term/{year}/{term}", method = RequestMethod.GET)
	public List<DataValues> findByTerm(@PathVariable("year") int year,
			@PathVariable("term") int term) {

		logger.debug("findByTerm(year, term) proces has been called.");
		return this.dataValueService.findTermData(year, term);
	}

	@RequestMapping(value = "/values/annual/{year}", method = RequestMethod.GET)
	public List<DataValues> findAnnualData(int year) {

		logger.debug("findAnnualData(year) proces has been called.");
		return this.dataValueService.findAnnualData(year);
	}

	@RequestMapping(value = "/values/{year}/sites/{id}", method = RequestMethod.GET)
	public List<DataValues> searchBySites(
			@PathVariable("id") List<Integer> sIDList,
			@PathVariable("year") int year) {

		logger.debug("searchBySite(year, sIDList) proces has been called.");

		return this.dataValueService.searchBySites(year, sIDList);

	}

	@RequestMapping(value = "/values/{year}/fishes/{id}", method = RequestMethod.GET)
	public List<DataValues> searchByFish(
			@PathVariable("id") List<Integer> fIDList,
			@PathVariable("year") int year) {

		logger.debug("searchByFish(year,fIDList) proces has been called.");

		return this.dataValueService.searchByFish(year, fIDList);

	}

	@RequestMapping(value = "/values/{start}/{end}/sites/{id}", method = RequestMethod.GET)
	public List<DataValues> searchBySites(
			@PathVariable("id") List<Integer> sIDList,
			@PathVariable("start") String start, @PathVariable("end") String end) {

		logger.info("searchBySite(start, end, sIDList) proces has been called.");
		logger.info(start + ", " + end + ", " + sIDList);
		return this.dataValueService.searchBySites(start, end, sIDList);

	}

	// ---------------------get all value by (year/term +
	// siteID)-------------------------------------
	@RequestMapping(value = "/values/term/{year}/{term}/sites/{id}", method = RequestMethod.GET)
	public List<DataValues> searchBySites(
			@PathVariable("id") List<Integer> sIDList,
			@PathVariable("year") int year, @PathVariable("term") int term) {

		logger.info("searchTermBySite(year, term, sIDList) proces has been called.");
		// logger.info("searchTermBySite(year, term, sIDList): YEAR:{}, TERM:{}, SITEID LIST:{}",
		// year, term, sIDList);
		logger.info(Integer.toString(year) + ", " + Integer.toString(term)
				+ ", " + sIDList);
		return this.dataValueService.searchBySites(year, term, sIDList);
	}

	// ----------------------get **fish** value by (year/term +
	// siteID)------------------------------
	@RequestMapping(value = "/values/term/{year}/{term}/fish/{id}", method = RequestMethod.GET)
	public List<DataValues> searchByFish(
			@PathVariable("id") List<Integer> fIDList,
			@PathVariable("year") int year, @PathVariable("term") int term) {

		logger.debug("searchByFish(year,term,fIDList) proces has been called.");

		return this.dataValueService.searchByFish(year, term, fIDList);

	}

	@RequestMapping(value = "/values/years", method = RequestMethod.GET)
	public List<Integer> searchSurveyYears() {

		logger.info("searchSurveyYears() proces has been called.");

		return this.dataValueService.searchSurveyYears();

	}
}