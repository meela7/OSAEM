package org.galilee.dms.controller;

import java.util.List;

import org.galilee.dms.model.SiteCode;
import org.galilee.dms.service.SiteCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiteCodeController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(SiteCodeController.class);

	@Autowired
	private SiteCodeService siteCodeService;

	@RequestMapping(value = "/siteCode/create", method = RequestMethod.POST)
	public void create(@RequestBody SiteCode siteCode) {

		this.siteCodeService.add(siteCode);
		logger.info("add() proces has been called.");
	}

	@RequestMapping(value = "/siteCode/update", method = RequestMethod.POST)
	public void update(@RequestBody SiteCode siteCode) {

		logger.info("update() proces has been called.");
		this.siteCodeService.update(siteCode);
	}

	@RequestMapping(value = "/siteCode/delete", method = RequestMethod.POST)
	public void delete(@RequestBody int siteCodeID) {

		this.siteCodeService.delete(siteCodeID);
		logger.info("delete() proces has been called.");
	}

	@RequestMapping(value = "/siteCode/{id}", method = RequestMethod.GET)
	public SiteCode find(@PathVariable("id") int siteCodeID) {

		logger.info("find() proces has been called.");
		return this.siteCodeService.findByID(siteCodeID);
	}

	@RequestMapping(value = "/siteCodes", method = RequestMethod.GET)
	public List<SiteCode> findAll() {

		logger.info("findAll() proces has been called.");
		return this.siteCodeService.findAll();
	}

}