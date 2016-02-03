package org.galilee.dms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.HashMap;
import java.util.List;

import org.galilee.dms.model.SiteInfo;
import org.galilee.dms.model.Sites;
import org.galilee.dms.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Api(value = "sites")
@RestController
public class SiteController {
	private static final Logger logger = LoggerFactory
			.getLogger(SiteController.class);

	@Autowired
	private SiteService siteService;
	/*
	 * Services - basic site web resource
	 */
	@ApiOperation(value = "Creates a Site")
	@RequestMapping(value = "/sites", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Sites site, UriComponentsBuilder ucBuilder) {
		logger.debug("Creating Site: {} ..." + site.getSiteName());
		if(siteService.isExist(site)){
			logger.debug("A Site with name {} already exist.", site.getSiteName() );
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}			
		this.siteService.add(site);
		
		HttpHeaders header = new HttpHeaders();
		header.setLocation(ucBuilder.path("/sites/{id}").buildAndExpand(site.getSiteID()).toUri());
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}	
	
	@ApiOperation(value = "Read a Site")
	@RequestMapping(value = "/sites/{id}", method = RequestMethod.GET)
	public ResponseEntity<Sites> find(@PathVariable("id") int siteID) {
		logger.debug("Fetching Site with ID: {} ...", siteID);
		Sites site = this.siteService.findByID(siteID);
		if(site == null){
			logger.debug("Site with ID: {}, not found.", siteID );
			return new ResponseEntity<Sites>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Sites>(site, HttpStatus.OK);
	}

	@ApiOperation(value = "Update a Site")
	@RequestMapping(value = "/sites/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Sites site, @PathVariable("id") int siteID) {
		
		logger.debug("Updating Site: {} ...", site.getSiteName());		
		if(siteID != site.getSiteID()){
			logger.debug("Site ID: {} , {} doesn't match.", siteID, site.getSiteID());
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}else {
			Sites oldSite = this.siteService.findByID(siteID);
			if(oldSite == null){
				logger.debug("Site with ID: {}, not found.", siteID);
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		}
		this.siteService.update(site);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete a Site")
	@RequestMapping(value = "/sites/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable int siteID) {
		logger.debug("Fetching & Deleting Site with ID {} ...", siteID);
		Sites site = this.siteService.findByID(siteID);
		if(site == null){
			logger.debug("Unable to delete. Site with id: {}, not found.", siteID);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		this.siteService.delete(siteID);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}	

	/*
	 * Service - collection site web resource
	 */
	@ApiOperation(value = "Read all the Sites.")
	@RequestMapping(value = "/sites", method = RequestMethod.GET)
	public ResponseEntity<List<Sites>> findAll() {
		logger.debug("Fetchin All Sites ...");
		List<Sites> sites = this.siteService.findAll();
		if(sites.isEmpty()){
			logger.debug("No Sites found.");
			return new ResponseEntity<List<Sites>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Sites>>(sites, HttpStatus.OK);
	}
	
	/*
	 * Service - ReqestParam as Map
	 */
	@ApiOperation(value = "Retrieve Sites from a River based on its id")
	@RequestMapping(value = "/sites/search", method = RequestMethod.GET)
	public ResponseEntity<List<Sites>> searchSites(@RequestParam HashMap<String, String> map) {
		
		List<Sites> sites = this.siteService.findAll();
		return new ResponseEntity<List<Sites>>(sites, HttpStatus.OK);
	}
	
	/*
	 * Services - RequestParam as key:value pair
	 */
	@ApiOperation(value = "Retrieve Sites from a River based on its id")
	@RequestMapping(value = "/rivers/{id}/sites", method = RequestMethod.GET)
	public ResponseEntity<List<Sites>> findByRiver(@PathVariable("id") int riverID) {

		logger.debug("Searching list of Sites in River: {} ... ", riverID);
		List<Sites> sites = this.siteService.findByRiver(riverID);
		if(sites.size() == 0){
			logger.debug("Sites in River: {},  not found." + riverID);
			return new ResponseEntity<List<Sites>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Sites>>(sites, HttpStatus.OK);			
	}
	
	@ApiOperation(value = "Retrieve Sites based their ids")
	@RequestMapping(value = "/sites", params = "list", method = RequestMethod.GET)
	public ResponseEntity<List<Sites>> findSites(@ApiParam(value = "list of Site ID", required = false) @RequestParam("list") List<Integer> siteIDs) {
		
		logger.debug("Fetching Sites by {} IDs", siteIDs.size());
		List<Sites> sites = this.siteService.findByIDs(siteIDs);
		if(sites.size() == 0){
			logger.debug("Fetching Sites by the list of IDs have no result.");
			return new ResponseEntity<List<Sites>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Sites>>(sites, HttpStatus.OK);
	}
		
	@ApiOperation(value = "Retrieve Sites based on the name")
	@RequestMapping(value = "/sites", params = "name", method = RequestMethod.GET)
	public ResponseEntity<List<Sites>> find(@ApiParam(value = "Site Name", required = false) @RequestParam("name") String name) {

		logger.debug("Searching Site by name: {}", name);
		List<Sites> sites = this.siteService.findByName(name);
		if(sites.size() == 0){
			logger.debug("Searching Site by name: {} have no result.", name);
			return new ResponseEntity<List<Sites>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Sites>>(sites, HttpStatus.OK);
	}

	@ApiOperation(value = "Retrieve Sites based on the Basin")
	@RequestMapping(value = "/sites", params = "basin", method = RequestMethod.GET)
	public ResponseEntity<List<Sites>> findByBasin(@ApiParam(value = "Basin", required = false) @RequestParam("basin") String basin) {

		logger.debug("Searching Site by Basin: {} ...", basin);
		List<Sites> sites = this.siteService.findByBasin(basin);
		if(sites.size() == 0){
			logger.debug("Searching Site by Basin: {}, have no result.", basin);
			return new ResponseEntity<List<Sites>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Sites>>(sites, HttpStatus.OK); 			
	}
	
	@ApiOperation(value = "Retrieve Sites based on the Mid Watershed (중권역)")
	@RequestMapping(value = "/sites", params = "midWatershed", method = RequestMethod.GET)
	public ResponseEntity<List<Sites>> findByMid(@RequestParam("midWatershed") String mid) {

		logger.debug("Searching Site by mid watershed: {} ...", mid);
		List<Sites> sites = this.siteService.findByMid(mid);
		if(sites.size() == 0){
			logger.debug("Searching Site by mid watershed: {}, have no result.", mid);
			return new ResponseEntity<List<Sites>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Sites>>(sites, HttpStatus.OK); 
	}
	
	@ApiOperation(value = "Retrieve Sites based on the Sub Watershed (소권역)")
	@RequestMapping(value = "/sites", params = "subWatershed", method = RequestMethod.GET)
	public ResponseEntity<List<Sites>> findBySub(@RequestParam("subWatershed") String sub) {

		logger.debug("Searching Site by sub watershed: {} ...", sub);
		List<Sites> sites = this.siteService.findBySub(sub);
		if(sites.size() == 0){
			logger.debug("Searching Site by sub watershed: {}, have no result.", sub);
			return new ResponseEntity<List<Sites>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Sites>>(sites, HttpStatus.OK); 
	}
	
	@ApiOperation(value = "Retrieve Sites based on Basin and Classification")
	@RequestMapping(value = "/sites", params = {"basin", "classification"}, method = RequestMethod.GET)
	public ResponseEntity<List<Sites>> findByBasinClass(@RequestParam("basin") String basin, @RequestParam("classification") String classification) {

		logger.debug("Searching Site by sub watershed: {} and classification: {} ", basin, classification);
		List<Sites> sites = this.siteService.findByBasinClass(basin, classification);
		if(sites.size() == 0){
			logger.debug("Searching Site by sub watershed: {} and classification: {}, has no result.", basin, classification);
			return new ResponseEntity<List<Sites>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Sites>>(sites, HttpStatus.OK); 
	}
	
	@ApiOperation(value = "Retrieve Sites based on Basin and Stream Order")
	@RequestMapping(value = "/sites", params = {"basin", "order"}, method = RequestMethod.GET)
	public ResponseEntity<List<Sites>> findByBasinOrder(@RequestParam("basin") String basin, @RequestParam("order") int streamOrder) {

		logger.debug("Searching Site by Basin: {} and Stream Order: {} ...", basin, streamOrder);
		List<Sites> sites = this.siteService.findByBasinOrder(basin, streamOrder);
		if(sites.size() == 0){
			logger.debug("Searching Site by Basin: {} and Stream Order: {}, has no result.", basin, streamOrder);
			return new ResponseEntity<List<Sites>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Sites>>(sites, HttpStatus.OK); 
	}
	
	/*
	 * Services - siteInfo 
	 */
	
	@RequestMapping(value = "/siteInfos", method = RequestMethod.GET)
	public List<SiteInfo> findAllSiteInfo() {

		logger.debug("findAllSiteInfo() proces has been called.");
		return this.siteService.findAllSiteInfo();
	}
	
	@RequestMapping(value = "/siteInfos", params = "list", method = RequestMethod.GET)
	public List<SiteInfo> findAllSiteInfos(@RequestParam("list") List<Integer> siIDs) {
		
		logger.debug("findSiteInfos() proces has been called.");
		return this.siteService.findInfoByIDs(siIDs);
	}	

	@RequestMapping(value = "/siteInfos", params = {"basin"}, method = RequestMethod.GET)
	public List<SiteInfo> findSiteInfosByBasin(@RequestParam("basin") String basin) {
		
		logger.debug("findSiteInfoByBasin() proces has been called.");
		return this.siteService.findInfosByBasin( basin);
	}

	@RequestMapping(value = "/rivers/{id}/siteInfos", method = RequestMethod.GET)
	public List<SiteInfo> findInfoByRiver(@PathVariable("id") List<Integer> rIDList) {

		logger.debug("findInfoByRiver() proces has been called.");

		logger.debug("RIVERIDS:{}",rIDList);
		return this.siteService.findInfoByRivers(rIDList);				
	}
}