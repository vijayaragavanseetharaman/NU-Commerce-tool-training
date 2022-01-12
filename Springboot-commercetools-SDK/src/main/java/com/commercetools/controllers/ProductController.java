package com.commercetools.controllers;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.commercetools.service.QueryProductTypeExamples;
import com.commercetools.service.TaxCategoryQuery;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(value = "/commercetools")
public class ProductController {
	private static final Logger logger = Logger.getLogger(ProductController.class);
	
	@Autowired
	private QueryProductTypeExamples queryProductTypeExamples;
	
	@RequestMapping(value = "/getProductCategory", method = RequestMethod.GET)
	public void getProductCategory() {
		logger.info("getProductCategory controller : ");
		queryProductTypeExamples.queryAll();		
	}	
	

	@RequestMapping(value = "/getProductForCategory", method = RequestMethod.GET)
	public void getProductForCategory() throws ExecutionException, InterruptedException {
		logger.info("getProductForCategory controller : ");
		logger.info(queryProductTypeExamples.getProductsForCategory());		
	}

}
