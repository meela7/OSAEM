package org.galilee.dms.controller;

import java.util.List;

import org.galilee.dms.model.Sources;
import org.galilee.dms.service.SourceService;
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

@RestController
@RequestMapping(value = "/api/v1")
public class SourceController {
	private static final Logger logger = LoggerFactory.getLogger(SourceController.class);
	
	@Autowired
	private SourceService sourceService;
	
	/*
	 * Services - basic source web resources
	 */
	//--------------------Create a Source--------------------
	@RequestMapping(value="/sources/new", method=RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Sources source){
		logger.debug("Creating Source: {} ..." + source.getInstitution());
		if(sourceService.findByInstitute(source.getInstitution()) != null){
			logger.debug("A Source with name {} already exist.", source.getInstitution() );
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		this.sourceService.add(source);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	//--------------------Read a Source--------------------
	@RequestMapping(value="/sources/{id}", method=RequestMethod.GET)
	public ResponseEntity<Sources> find(@PathVariable("id") int sourceID){
		logger.debug("Fetching Source with ID: {} ...", sourceID);
		Sources source = this.sourceService.findByID(sourceID);
		if(source == null){
			logger.debug("Source with ID: {}, not found.", sourceID );
			return new ResponseEntity<Sources>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Sources>(source, HttpStatus.OK);
	}
	
	//--------------------Update a Source--------------------
	@RequestMapping(value="/sources/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Sources source, @PathVariable("id") int sourceID){
		logger.debug("Updating Source: {} ...", source.getInstitution());		
		if(sourceID != source.getSourceID()){
			logger.debug("Source ID: {} , {} doesn't match.", sourceID, source.getSourceID());
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}else {
			Sources oldSource = this.sourceService.findByID(sourceID);
			if(oldSource == null){
				logger.debug("Source with ID: {}, not found.", sourceID);
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		}
		this.sourceService.update(source);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	//--------------------Delete a Source--------------------
	@RequestMapping(value="/sources/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody int sourceID){
		this.sourceService.delete(sourceID);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/*
	 * Services - source collection web resources
	 */
	//--------------------Retrieve All Sources--------------------
	@RequestMapping(value="/sources", method=RequestMethod.GET)
	public ResponseEntity<List<Sources>> findAll(){
		logger.debug("Fetchin All Sources ...");
		List<Sources> sources = sourceService.findAll();
		if(sources.isEmpty()){
			logger.debug("No Sources found.");
			return new ResponseEntity<List<Sources>>(HttpStatus.NO_CONTENT);
		}
        return new ResponseEntity<List<Sources>>(sources, HttpStatus.OK);
	}
	
	//--------------------Retrieve Sources by institute--------------------
	@RequestMapping(value="/sources", params="institute", method=RequestMethod.GET)
	public ResponseEntity<List<Sources>> findByInstitute(@RequestParam("institute") String institute){
		logger.debug("Searching Sources by institute: {}", institute);
        List<Sources> sources = this.sourceService.findByInstitute(institute);
        if(sources.size() == 0){
			logger.debug("Searching Sources by institute: {} have no result.", institute);
			return new ResponseEntity<List<Sources>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Sources>>(sources, HttpStatus.OK);
	}
	
	//--------------------Retrieve Sources by contact--------------------
	@RequestMapping(value="/sources", params="contact", method=RequestMethod.GET)
	public ResponseEntity<List<Sources>> findByContact(@RequestParam("contact") String contact){
		
		logger.debug("Searching Sources by contact: {}", contact);
		List<Sources> sources = this.sourceService.findByContact(contact);
        if(sources.size() == 0){
			logger.debug("Searching Sources by contact: {} have no result.", contact);
			return new ResponseEntity<List<Sources>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Sources>>(sources, HttpStatus.OK);
	}
}