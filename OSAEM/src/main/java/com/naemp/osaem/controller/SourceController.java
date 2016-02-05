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

import com.naemp.osaem.model.Source;
import com.naemp.osaem.service.SourceService;

import io.swagger.annotations.Api;

@Api(value = "Sources")
@RestController
public class SourceController {

	/**
	 * Class Name: SourceController.java 
	 * Description: CRUD, Service
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private static final Logger logger = LoggerFactory.getLogger(SourceController.class);

	@Autowired
	private SourceService sourceService;

	// -------------------- Read and Search Source Collection Resource
	// --------------------
	@RequestMapping(value = "/sources", method = RequestMethod.GET)
	public ResponseEntity<List<Source>> list(@RequestParam(required = false) Map<String, String> params) {
		// read Source collection resource when there is no parameter.
		if (params.isEmpty()) {
			logger.info("Reading Source Collection Resource ...");
			List<Source> sources = sourceService.readCollection();
			if (sources.isEmpty()) {
				logger.info("No Sources found.");
				return new ResponseEntity<List<Source>>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Source>>(sources, HttpStatus.OK);
		}
		// search Source collection resource with parameters.
		else {
			logger.info("Searching Source Resource ...");

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

			List<Source> sources = this.sourceService.search(map);
			return new ResponseEntity<List<Source>>(sources, HttpStatus.OK);
		}
	}

	// -------------------- Create a Source Instance Resource ------------------
	@RequestMapping(value = "/sources/new", method = RequestMethod.POST)
	public ResponseEntity<Integer> create(@RequestBody Source source, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Source Instance Resource of Name: {} ..." + source.getInstitution());
		// check if source contains the Not Null field in the database.
		if (source.getInstitution() == null || source.getInvestigator() == null)
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);

		if (sourceService.isInstanceExist(source.getInstitution(), source.getInvestigator())) {
			logger.info("A Source with name {} already exist.", source.getInstitution());
			return new ResponseEntity<Integer>(HttpStatus.CONFLICT);
		}

		int createdID = sourceService.newInstance(source);
		return new ResponseEntity<Integer>(createdID, HttpStatus.CREATED);
	}

	// -------------------- Read a Source Instance Resource --------------------
	@RequestMapping(value = "/sources/{id}", method = RequestMethod.GET)
	public ResponseEntity<Source> read(@PathVariable("id") int sourceID) {
		logger.info("Reading Source Instance Resource of ID: {} ...", sourceID);
		Source source = this.sourceService.readInstance(sourceID);
		if (source == null) {
			logger.info("Source Instance Resource of ID: {}, not found.", sourceID);
			return new ResponseEntity<Source>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Source>(source, HttpStatus.OK);
	}

	// -------------------- Update a Source Instance Resource ------------------
	@RequestMapping(value = "/sources/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Boolean> update(@RequestBody Source source, @PathVariable("id") int sourceID) {
		logger.info("Updating Source Instance Resource of ID: {} ...", source.getSourceID());

		if (sourceID != source.getSourceID()) {
			logger.info("Source Instance Resource of ID: {} , {} doesn't match.", sourceID, source.getSourceID());
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		} else {
			Source oldSource = this.sourceService.readInstance(sourceID);
			if (oldSource == null) {
				logger.info("Source Instance Resource of ID: {}, not found.", sourceID);
				return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
			}
		}
		// set the null of Source with oldSource

		Boolean res = this.sourceService.updateInstance(source);
		if (res)
			return new ResponseEntity<Boolean>(res, HttpStatus.OK);
		else
			return new ResponseEntity<Boolean>(res, HttpStatus.CONFLICT);
	}

	// -------------------- Delete a Source Instance Resource ------------------
	@RequestMapping(value = "/sources/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") int sourceID) {
		logger.info("Reading & Deleting Source Instance Resource of ID: {} ...", sourceID);
		Source source = this.sourceService.readInstance(sourceID);
		if (source == null) {
			logger.info("Unable to delete. Source Instance Resource of ID: {}, not found.", sourceID);
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
		}
		Boolean res = this.sourceService.deleteInstance(sourceID);
		if (res)
			return new ResponseEntity<Boolean>(res, HttpStatus.OK);
		else
			return new ResponseEntity<Boolean>(res, HttpStatus.CONFLICT);

	}

	// -------------------- Search for Source Resource --------------------
	@RequestMapping(value = "/sources", method = RequestMethod.POST)
	public ResponseEntity<List<Source>> search(@RequestBody Map<String, String> map) {
		logger.info("Searching Source Resource ...");

		for (String key : map.keySet()) {
			logger.info("The search parameter: {} ", key);
		}
		List<Source> sources = this.sourceService.search(map);
		if (sources.isEmpty() || sources == null)
			return new ResponseEntity<List<Source>>(sources, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<List<Source>>(sources, HttpStatus.OK);
	}

}
