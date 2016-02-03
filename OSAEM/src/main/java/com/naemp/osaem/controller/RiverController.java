package com.naemp.osaem.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.naemp.osaem.model.River;
import com.naemp.osaem.service.RiverService;

import io.swagger.annotations.Api;

@Api(value = "Rivers")
@RestController
public class RiverController {

	/**
	 * Class Name: RiverController.java 
	 * Description: CRUD, Service
	 * 
	 * @author Meilan Jiang
	 * @since 2016.01.30
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private static final Logger logger = LoggerFactory.getLogger(RiverController.class);

	@Autowired
	private RiverService riverService;

	// -------------------- Read and Search River Collection Resource --------------------
	@RequestMapping(value = "/rivers", method = RequestMethod.GET)
	public ResponseEntity<List<River>> list(@RequestParam(required = false) Map<String, String> params) {
		// read River collection resource when there is no param.
		if (params.isEmpty()) {
			logger.info("Reading River Collection Resource ...");
			List<River> rivers = riverService.readCollection();
			if (rivers.isEmpty()) {
				logger.info("No Rivers found.");
				return new ResponseEntity<List<River>>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<List<River>>(rivers, HttpStatus.OK);
		} else {
			logger.info("Searching River Resource ...");

			Map<String, String> map = new HashMap<String, String>();
			for (String key : params.keySet()) {				
				String param = key.substring(0,1).toUpperCase();
				param = param + key.substring(1);
				String value = params.get(key);
				try {
					map.put(param, new String(value.getBytes("8859_1"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// check if the URL encoded
//				if (value.contains("%")) {
//					logger.info("Parameter value: {} is encoded", value);
//					try {
//						map.put(key, new String(value.getBytes("8859_1"), "UTF-8"));
//					} catch (UnsupportedEncodingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				else{
//					logger.info("Parameter value: {} is not encoded", value);
//					map.put(key, value);
//				}
			}
			
			List<River> rivers = this.riverService.search(map);
			return new ResponseEntity<List<River>>(rivers, HttpStatus.OK);
		}
	}

	// -------------------- Create a River Instance Resource ------------------
	@RequestMapping(value = "/rivers/new", method = RequestMethod.POST)
	public ResponseEntity<Integer> create(@RequestBody River river, UriComponentsBuilder ucBuilder) {
		logger.info("Creating River Instance Resource of Name: {} ..." + river.getRiverName());
		if (riverService.isInstanceExist(river.getRiverName(), river.getSubWatershed(), river.getSubWatershed())) {
			logger.info("A River with name {} already exist.", river.getRiverName());
			return new ResponseEntity<Integer>(HttpStatus.CONFLICT);
		}
		int createdID = riverService.newInstance(river);
		return new ResponseEntity<Integer>(createdID, HttpStatus.CREATED);
	}

	// -------------------- Read a River Instance Resource --------------------
	@RequestMapping(value = "/rivers/{id}", method = RequestMethod.GET)
	public ResponseEntity<River> read(@PathVariable("id") int riverID) {
		logger.info("Reading River Instance Resource of ID: {} ...", riverID);
		River river = this.riverService.readInstance(riverID);
		if (river == null) {
			logger.info("River Instance Resource of ID: {}, not found.", riverID);
			return new ResponseEntity<River>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<River>(river, HttpStatus.OK);
	}

	// -------------------- Update a River Instance Resource
	// --------------------
	@RequestMapping(value = "/rivers/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Boolean> update(@RequestBody River river, @PathVariable("id") int riverID) {
		logger.info("Updating River Instance Resource of ID: {} ...", river.getRiverID());

		if (riverID != river.getRiverID()) {
			logger.info("River Instance Resource of ID: {} , {} doesn't match.", riverID, river.getRiverID());
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		} else {
			River oldRiver = this.riverService.readInstance(riverID);
			if (oldRiver == null) {
				logger.info("River Instance Resource of ID: {}, not found.", riverID);
				return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
			}
		}
		Boolean res = this.riverService.updateInstance(river);
		return new ResponseEntity<Boolean>(res, HttpStatus.OK);
	}

	// -------------------- Delete a River Instance Resource
	// --------------------
	@RequestMapping(value = "/rivers/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable int riverID) {
		logger.info("Reading & Deleting River Instance Resource of ID: {} ...", riverID);
		River river = this.riverService.readInstance(riverID);
		if (river == null) {
			logger.info("Unable to delete. River Instance Resource of ID: {}, not found.", riverID);
			return new ResponseEntity<Boolean>(false, HttpStatus.NO_CONTENT);
		}
		Boolean res = this.riverService.deleteInstance(riverID);
		return new ResponseEntity<Boolean>(res, HttpStatus.OK);
	}

	// -------------------- Search for River Resource --------------------
	@RequestMapping(value = "/rivers", method = RequestMethod.POST)
	public ResponseEntity<List<River>> search(@RequestBody Map<String, String> map) {
		logger.info("Searching River Resource ...");

		for (String key : map.keySet()) {
			logger.info("The search parameter: {} ", key);
		}
		List<River> rivers = this.riverService.search(map);
		return new ResponseEntity<List<River>>(rivers, HttpStatus.OK);
	}

}
