package com.commercetools.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.commercetools.service.CartService;
import com.commercetools.service.QueryProductTypeExamples;

@Controller
@RequestMapping(value = "/commercetools")
public class CartController {
	private static final Logger logger = Logger.getLogger(CartController.class);
	@Autowired
	private CartService cartService;
	
	
	@RequestMapping(value = "/getCart/{cartId}", method = RequestMethod.GET)
	public void getCart(@PathVariable("cartId") String cartId) {
		logger.info("getCart controller : ");
		cartService.getCart(cartId, true);		
	}	
	
	
	@RequestMapping(value = "/getCartByCustomerId", method = RequestMethod.GET)
	public void getCartByCustomerId(@PathVariable("cartId") String cartId) {
		logger.info("getCart controller : ");
		cartService.getCartByCustomerId(cartId, true);		
	}	
}
