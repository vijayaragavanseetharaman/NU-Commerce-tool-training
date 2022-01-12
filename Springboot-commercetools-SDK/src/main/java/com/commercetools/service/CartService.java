package com.commercetools.service;

import java.util.concurrent.CompletionStage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercetools.controllers.TaxCategoryQueryController;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartState;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.carts.queries.CartByCustomerIdGet;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.producttypes.ProductType;

@Service
public class CartService {
	private static final Logger logger = Logger.getLogger(CartService.class);
	@Autowired
    private BlockingSphereClient client;
    private ProductType productType;
    
    
    
    public CartState getCart(String cartId, boolean expand) {
    	logger.info("getCart : " + cartId);
        CartByIdGet cartByIdGet = CartByIdGet.of(cartId);
        logger.info("cartByIdGet : " + cartByIdGet);
        if (expand) {
            cartByIdGet = cartByIdGet
                .plusExpansionPaths(CartExpansionModel.of().discountCodes().discountCode())
                //.plusExpansionPaths(CartExpansionModel.of().paymentInfo().payments())
                .plusExpansionPaths(CartExpansionModel.of().lineItems().variant().attributes().value());
        }
        logger.info("cartByIdGet values: " + cartByIdGet.toString());
        logger.info("state value: " + client.executeBlocking(cartByIdGet).getCartState());
        return client.executeBlocking(cartByIdGet).getCartState();
    }
    
    public CompletionStage<Cart> getCartByCustomerId(String cartId, boolean expand) {
    	final CompletionStage<Cart> cartStage =
    	    client.execute(CartByCustomerIdGet.of("customer-id"));
    	return cartStage;
    }

}
