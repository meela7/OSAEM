package com.naemp.osaem.controller;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.naemp.osaem.model.Site;
import com.naemp.osaem.service.SiteService;

import io.swagger.annotations.Api;

@Api(value = "Sites")
@RestController
public class SiteController {

	/**
	 * Class Name: SiteController.java 
	 * Description: CRUD, Search
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.01
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */

	private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

	@Autowired
	private SiteService siteService;

	// -------------------- Read and Search Site Collection Resource --------------------
	@RequestMapping(value = "/sites", method = RequestMethod.GET)
	public ResponseEntity<List<Site>> list(@RequestParam(required = false) MultiValueMap<String, String> params) {
		// read River collection resource when there is no parameter.
		if (params.isEmpty()) {
			logger.info("Reading Site Collection Resource ...");
			List<Site> sites = siteService.readCollection();
			if (sites.isEmpty()) {
				logger.info("No Sites found.");
				return new ResponseEntity<List<Site>>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Site>>(sites, HttpStatus.OK);
		}
		// search Site collection resource with parameters.
		else {
			logger.info("Searching Site Resource ...");
			
			PropertyDescriptor[] props = BeanUtils.getPropertyDescriptors(Site.class);
			List<String> variables = new ArrayList<String>();
			for (PropertyDescriptor desc : props) {
				variables.add(desc.getName());
			}

			Map<String, List<String>> map = new HashMap<String, List<String>>();
			for (String key : params.keySet()) {
				if(variables.contains(key)){
					// uppercase first letter of property name
					String param = key.substring(0,1).toUpperCase();
					param = param + key.substring(1);				
				
					List<String> values = new ArrayList<String>();
					for(String value: params.get(key)){
						// decode parameters
						try {
							values.add(new String(value.getBytes("8859_1"), "UTF-8"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
					map.put(param, values);			
				}
				else
					logger.info("Unexpected Parameter :{} has been removed.", key);
			}
			if(map.keySet().size() == 0){
				return new ResponseEntity<List<Site>>(HttpStatus.BAD_REQUEST);
			}else{
				List<Site> sites = this.siteService.listSearch(map);
				if(sites == null || sites.isEmpty())
					return new ResponseEntity<List<Site>>(HttpStatus.NOT_FOUND);
				else
					return new ResponseEntity<List<Site>>(sites, HttpStatus.OK);
			}
		}
	}

	// -------------------- Create a Site Instance Resource --------------------
	@RequestMapping(value = "/sites/new", method = RequestMethod.POST)
	public ResponseEntity<Integer> create(@RequestBody Site site, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Site Instance Resource of Name: {} ..." + site.getSiteName());
		
		if (site.getSiteName() == null || site.getLatitude() == null || site.getLongitude() == null ) 
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		
		if (siteService.isInstanceExist(site.getSiteName(), site.getLatitude(), site.getLongitude())) {
			logger.info("A Site with name {} already exist.", site.getSiteName());
			return new ResponseEntity<Integer>(HttpStatus.CONFLICT);
		}
		int createdID = siteService.newInstance(site);
		return new ResponseEntity<Integer>(createdID, HttpStatus.CREATED);
	}

	// -------------------- Read a Site Instance Resource --------------------
	@RequestMapping(value = "/sites/{id}", method = RequestMethod.GET)
	public ResponseEntity<Site> find(@PathVariable("id") int siteID) {
		logger.info("Reading Site Instance Resource of ID: {} ...", siteID);
		Site site = this.siteService.readInstance(siteID);
		if (site == null) {
			logger.info("Site Instance Resource of ID: {}, not found.", siteID);
			return new ResponseEntity<Site>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Site>(site, HttpStatus.OK);
	}
	
//	// -------------------- Read a list of Sites(used by Value Map) --------------------
//	@RequestMapping(value = "/sites/list/{id}", method = RequestMethod.GET)
//	public ResponseEntity<List<Site>> findByIDSet(@PathVariable("id") List<Integer> siteIDs) {
//		logger.debug("Fetching Sites by {} IDs", siteIDs.size());
//		List<Site> sites = this.siteService.findByIDs(siteIDs);
//		if(sites.size() == 0){
//			logger.debug("Fetching Sites by the list of IDs have no result.");
//			return new ResponseEntity<List<Site>>(HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<List<Site>>(sites, HttpStatus.OK);
//	}

	// -------------------- Update a Site Instance Resource --------------------
	@RequestMapping(value = "/sites/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Boolean> update(@RequestBody Site site, @PathVariable("id") int siteID) {
		logger.info("Updating Site Instance Resource of ID: {} ...", site.getSiteID());

		if (siteID != site.getSiteID()) {
			logger.info("Site Instance Resource of ID: {} , {} doesn't match.", siteID, site.getSiteID());
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		} else {
			Site oldSite = this.siteService.readInstance(siteID);
			if (oldSite == null) {
				logger.info("Site Instance Resource of ID: {}, not found.", siteID);
				return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
			}
		}
		// set the null of Site with oldSite
		
		Boolean res = this.siteService.updateInstance(site);
		if(res)
			return new ResponseEntity<Boolean>(res, HttpStatus.OK);
		else
			return new ResponseEntity<Boolean>(res, HttpStatus.CONFLICT);
	}

	// -------------------- Delete a Site Instance Resource --------------------
	@RequestMapping(value = "/sites/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") int siteID) {
		logger.info("Reading & Deleting Site Instance Resource of ID: {} ...", siteID);
		Site site = this.siteService.readInstance(siteID);
		if (site == null) {
			logger.info("Unable to delete. Site Instance Resource of ID: {}, not found.", siteID);
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
		}
		Boolean res = this.siteService.deleteInstance(siteID);
		if(res)
			return new ResponseEntity<Boolean>(res, HttpStatus.OK);
		else
			return new ResponseEntity<Boolean>(res, HttpStatus.CONFLICT);
	}
	
	// -------------------- Search for Site Resource (parameter map value is list) --------------------
	@RequestMapping(value = "/sites", method = RequestMethod.POST)
	public ResponseEntity<List<Site>> listSearch(@RequestBody HashMap<String, List<String>> reqMap) {
		logger.info("Searching Site Resource ...");

		PropertyDescriptor[] params = BeanUtils.getPropertyDescriptors(Site.class);
		List<String> variables = new ArrayList<String>();
		for (PropertyDescriptor desc : params) {
			variables.add(desc.getName());
		}
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (String key : reqMap.keySet()) {
			
			if(variables.contains(key)){	
				String param = key.substring(0,1).toUpperCase();
				param = param + key.substring(1);
				map.put(param, reqMap.get(key));
			}
			else
				logger.info("Unexpected Parameter :{} has been removed.", key);
		}
		if(map.keySet().size() == 0){
			return new ResponseEntity<List<Site>>(HttpStatus.BAD_REQUEST);
		}else{
			List<Site> sites = this.siteService.listSearch(map);
			if(sites == null || sites.isEmpty())
				return new ResponseEntity<List<Site>>(HttpStatus.NOT_FOUND);
			else
				return new ResponseEntity<List<Site>>(sites, HttpStatus.OK);
		}
	}
}
