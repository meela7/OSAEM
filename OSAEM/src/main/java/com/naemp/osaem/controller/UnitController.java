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

import com.naemp.osaem.model.Unit;
import com.naemp.osaem.service.UnitService;

import io.swagger.annotations.Api;

@Api(value = "Units")
@RestController
public class UnitController {

	/**
	 * Class Name: UnitController.java 
	 * Description: CRUD, Service
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UnitController.class);

	@Autowired
	private UnitService unitService;

	// -------------------- Read and Search Unit Collection Resource
	// --------------------
	@RequestMapping(value = "/units", method = RequestMethod.GET)
	public ResponseEntity<List<Unit>> list(@RequestParam(required = false) Map<String, String> params) {
		// read Unit collection resource when there is no parameter.
		if (params.isEmpty()) {
			logger.info("Reading Unit Collection Resource ...");
			List<Unit> units = unitService.readCollection();
			if (units.isEmpty()) {
				logger.info("No Units found.");
				return new ResponseEntity<List<Unit>>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Unit>>(units, HttpStatus.OK);
		}
		// search Unit collection resource with parameters.
		else {
			logger.info("Searching Unit Resource ...");

			Map<String, String> map = new HashMap<String, String>();
			for (String key : params.keySet()) {
				logger.info("Parameter :{}", key);
				// uppercase first letter of property name
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

			List<Unit> units = this.unitService.search(map);
			return new ResponseEntity<List<Unit>>(units, HttpStatus.OK);
		}
	}

	// -------------------- Create a Unit Instance Resource ------------------
	@RequestMapping(value = "/units/new", method = RequestMethod.POST)
	public ResponseEntity<Integer> create(@RequestBody Unit unit, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Unit Instance Resource of Name: {} ..." + unit.getUnitName());
		// check if unit contains the Not Null field in the database.
		if (unit.getUnitName() == null)
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);

		if (unitService.isInstanceExist(unit.getUnitName())) {
			logger.info("A Unit with name {} already exist.", unit.getUnitName());
			return new ResponseEntity<Integer>(HttpStatus.CONFLICT);
		}

		int createdID = unitService.newInstance(unit);
		return new ResponseEntity<Integer>(createdID, HttpStatus.CREATED);
	}

	// -------------------- Read a Unit Instance Resource --------------------
	@RequestMapping(value = "/units/{id}", method = RequestMethod.GET)
	public ResponseEntity<Unit> read(@PathVariable("id") int unitID) {
		logger.info("Reading Unit Instance Resource of ID: {} ...", unitID);
		Unit unit = this.unitService.readInstance(unitID);
		if (unit == null) {
			logger.info("Unit Instance Resource of ID: {}, not found.", unitID);
			return new ResponseEntity<Unit>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Unit>(unit, HttpStatus.OK);
	}

	// -------------------- Update a Unit Instance Resource ------------------
	@RequestMapping(value = "/units/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Boolean> update(@RequestBody Unit unit, @PathVariable("id") int unitID) {
		logger.info("Updating Unit Instance Resource of ID: {} ...", unit.getUnitID());

		if (unitID != unit.getUnitID()) {
			logger.info("Unit Instance Resource of ID: {} , {} doesn't match.", unitID, unit.getUnitID());
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		} else {
			Unit oldUnit = this.unitService.readInstance(unitID);
			if (oldUnit == null) {
				logger.info("Unit Instance Resource of ID: {}, not found.", unitID);
				return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
			}
		}
		// set the null of Unit with oldUnit

		Boolean res = this.unitService.updateInstance(unit);
		if (res)
			return new ResponseEntity<Boolean>(res, HttpStatus.OK);
		else
			return new ResponseEntity<Boolean>(res, HttpStatus.CONFLICT);
	}

	// -------------------- Delete a Unit Instance Resource ------------------
	@RequestMapping(value = "/units/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") int unitID) {
		logger.info("Reading & Deleting Unit Instance Resource of ID: {} ...", unitID);
		Unit unit = this.unitService.readInstance(unitID);
		if (unit == null) {
			logger.info("Unable to delete. Unit Instance Resource of ID: {}, not found.", unitID);
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
		}
		Boolean res = this.unitService.deleteInstance(unitID);
		if (res)
			return new ResponseEntity<Boolean>(res, HttpStatus.OK);
		else
			return new ResponseEntity<Boolean>(res, HttpStatus.CONFLICT); // when
																			// Unit
																			// has
																			// existing
																			// related
																			// Sites
	}

	// -------------------- Search for Unit Resource --------------------
	@RequestMapping(value = "/units", method = RequestMethod.POST)
	public ResponseEntity<List<Unit>> search(@RequestBody Map<String, String> map) {
		logger.info("Searching Unit Resource ...");

		for (String key : map.keySet()) {
			logger.info("The search parameter: {} ", key);
		}
		List<Unit> units = this.unitService.search(map);
		if (units.isEmpty() || units == null)
			return new ResponseEntity<List<Unit>>(units, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<List<Unit>>(units, HttpStatus.OK);
	}

}
