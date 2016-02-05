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

import com.naemp.osaem.model.Fish;
import com.naemp.osaem.service.FishService;

import io.swagger.annotations.Api;

@Api(value = "Fishes")
@RestController
public class FishController {

	/**
	 * Class Name: FishController.java 
	 * Description: CRUD, Service
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FishController.class);

	@Autowired
	private FishService fishService;

	// -------------------- Read and Search Fish Collection Resource
	// --------------------
	@RequestMapping(value = "/fishes", method = RequestMethod.GET)
	public ResponseEntity<List<Fish>> list(@RequestParam(required = false) Map<String, String> params) {
		// read Fish collection resource when there is no parameter.
		if (params.isEmpty()) {
			logger.info("Reading Fish Collection Resource ...");
			List<Fish> fishes = fishService.readCollection();
			if (fishes.isEmpty()) {
				logger.info("No Fishs found.");
				return new ResponseEntity<List<Fish>>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Fish>>(fishes, HttpStatus.OK);
		}
		// search Fish collection resource with parameters.
		else {
			logger.info("Searching Fish Resource ...");

			Map<String, String> map = new HashMap<String, String>();
			for (String key : params.keySet()) {
				logger.info("Parameter :{}", key);
				// uppercase first letter of property name
				if(key.equals("fishClass") || key.equals("FishClass")){
					key = "Class";
					
				}
				String param = key.substring(0, 1).toUpperCase();
				param = param + key.substring(1);
				
				String value = params.get(key);

				// decode parameters
				try {
					map.put(param, new String(value.getBytes("8859_1"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// check if the URL encoded
				// if (value.contains("%")) {
				// logger.info("Parameter value: {} is encoded", value);
				// try {
				// map.put(key, new String(value.getBytes("8859_1"), "UTF-8"));
				// } catch (UnsupportedEncodingException e) {
				// e.printStackTrace();
				// }
				// }
				// else{
				// logger.info("Parameter value: {} is not encoded", value);
				// map.put(key, value);
				// }
			}

			List<Fish> fishes = this.fishService.search(map);
			return new ResponseEntity<List<Fish>>(fishes, HttpStatus.OK);
		}
	}

	// -------------------- Create a Fish Instance Resource ------------------
	@RequestMapping(value = "/fishes/new", method = RequestMethod.POST)
	public ResponseEntity<Integer> create(@RequestBody Fish fish, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Fish Instance Resource of Name: {} ..." + fish.getSpecies());
		// check if fish contains the Not Null field in the database.
		if (fish.getSpecies() == null)
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);

		if (fishService.isInstanceExist(fish.getSpecies())) {
			logger.info("A Fish with name {} already exist.", fish.getSpecies());
			return new ResponseEntity<Integer>(HttpStatus.CONFLICT);
		}

		int createdID = fishService.newInstance(fish);
		return new ResponseEntity<Integer>(createdID, HttpStatus.CREATED);
	}

	// -------------------- Read a Fish Instance Resource --------------------
	@RequestMapping(value = "/fishes/{id}", method = RequestMethod.GET)
	public ResponseEntity<Fish> read(@PathVariable("id") int fishID) {
		logger.info("Reading Fish Instance Resource of ID: {} ...", fishID);
		Fish fish = this.fishService.readInstance(fishID);
		if (fish == null) {
			logger.info("Fish Instance Resource of ID: {}, not found.", fishID);
			return new ResponseEntity<Fish>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Fish>(fish, HttpStatus.OK);
	}

	// -------------------- Update a Fish Instance Resource ------------------
	@RequestMapping(value = "/fishes/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Boolean> update(@RequestBody Fish fish, @PathVariable("id") int fishID) {
		logger.info("Updating Fish Instance Resource of ID: {} ...", fish.getFishID());

		if (fishID != fish.getFishID()) {
			logger.info("Fish Instance Resource of ID: {} , {} doesn't match.", fishID, fish.getFishID());
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		} else {
			Fish oldFish = this.fishService.readInstance(fishID);
			if (oldFish == null) {
				logger.info("Fish Instance Resource of ID: {}, not found.", fishID);
				return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
			}
		}
		// set the null of Fish with oldFish

		Boolean res = this.fishService.updateInstance(fish);
		if (res)
			return new ResponseEntity<Boolean>(res, HttpStatus.OK);
		else
			return new ResponseEntity<Boolean>(res, HttpStatus.CONFLICT);
	}

	// -------------------- Delete a Fish Instance Resource ------------------
	@RequestMapping(value = "/fishes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") int fishID) {
		logger.info("Reading & Deleting Fish Instance Resource of ID: {} ...", fishID);
		Fish fish = this.fishService.readInstance(fishID);
		if (fish == null) {
			logger.info("Unable to delete. Fish Instance Resource of ID: {}, not found.", fishID);
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
		}
		Boolean res = this.fishService.deleteInstance(fishID);
		if (res)
			return new ResponseEntity<Boolean>(res, HttpStatus.OK);
		else
			return new ResponseEntity<Boolean>(res, HttpStatus.CONFLICT); // when
																			// Fish
																			// has
																			// existing
																			// related
																			// Sites
	}

	// -------------------- Search for Fish Resource --------------------
	@RequestMapping(value = "/fishes", method = RequestMethod.POST)
	public ResponseEntity<List<Fish>> search(@RequestBody Map<String, String> map) {
		logger.info("Searching Fish Resource ...");

		for (String key : map.keySet()) {
			logger.info("The search parameter: {} ", key);
		}
		List<Fish> fish = this.fishService.search(map);
		if (fish.isEmpty() || fish == null)
			return new ResponseEntity<List<Fish>>(fish, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<List<Fish>>(fish, HttpStatus.OK);
	}

}
