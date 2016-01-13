package org.galilee.dms.controller;

import java.util.List;

import org.galilee.dms.model.Features;
import org.galilee.dms.service.FeatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeatureController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(FeatureController.class);

	@Autowired
	private FeatureService featureService;
	/*
	 * Services - basic feature web resource
	 */
	//--------------------Create a Feature--------------------
	@RequestMapping(value = "/features/new", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Features feature) {
		logger.debug("Creating Feature: {} ..." + feature.getFeatureName());
		if(featureService.findByName(feature.getFeatureName()) != null){
			logger.debug("A Feature with name {} already exist.", feature.getFeatureName() );
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		this.featureService.add(feature);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	//--------------------Retrieve a Feature--------------------
	@RequestMapping(value = "/features/{id}", method = RequestMethod.GET)
	public ResponseEntity<Features> find(@PathVariable("id") int featureID) {

		logger.debug("Fetching Feature with ID: {} ...", featureID);
		Features feature = this.featureService.findByID(featureID);
		if(feature== null){
			logger.debug("A Feature with ID: {}, not found.", featureID );
			return new ResponseEntity<Features>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Features>(feature, HttpStatus.OK);
	}

	//--------------------Update a Feature--------------------
	@RequestMapping(value = "/features/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Features feature, @PathVariable("id") int featureID) {

		
		logger.debug("Updating Feature: {} ...", feature.getFeatureName());		
		if(featureID != feature.getFeatureID()){
			logger.debug("Feature ID: {} , {} doesn't match.", featureID, feature.getFeatureID());
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}else {
			Features oldFeature = this.featureService.findByID(featureID);
			if(oldFeature == null){
				logger.debug("Feature with ID: {}, not found.", featureID);
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		}
		this.featureService.update(feature);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	//--------------------Delete a Feature--------------------
	@RequestMapping(value = "/features/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable int featureID) {

		
		logger.debug("Fetching & Deleting Feature with ID {} ...", featureID);
		Features feature = this.featureService.findByID(featureID);
		if(feature == null){
			logger.debug("Unable to delete. Feature with id: {}, not found.", featureID);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		this.featureService.delete(feature);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/*
	 * Services - collection feature web resource
	 */
	//--------------------Retrieve All Features--------------------
	@RequestMapping(value = "/features", method = RequestMethod.GET)
	public ResponseEntity<List<Features>> findAll() {

		logger.info("Fetchin All Features ...");
		List<Features> features = this.featureService.findAll();
		if(features.size() == 0){
			logger.debug("No Features found.");
			return new ResponseEntity<List<Features>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Features>>(features, HttpStatus.OK);	
	}

}