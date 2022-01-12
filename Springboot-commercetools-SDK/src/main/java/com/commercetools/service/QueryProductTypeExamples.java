package com.commercetools.service;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.*;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.producttypes.queries.ProductTypeQueryModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodQuery;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartState;
import io.sphere.sdk.carts.TaxMode;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.carts.queries.CartByCustomerIdGet;
import io.sphere.sdk.carts.queries.CartByIdGet;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.commercetools.controllers.ProductController;
import com.neovisionaries.i18n.CountryCode;

@Service
public class QueryProductTypeExamples {
	private static final Logger logger = Logger.getLogger(QueryProductTypeExamples.class);
	
	@Autowired
    private BlockingSphereClient client;
    private ProductType productType;
    Cart cart = null;

    public void queryAll() {
    	logger.info("queryAll service : ");
    	logger.info("client : "+client.getConfig().getProjectKey());
        final CompletionStage<PagedQueryResult<ProductType>> result = client.execute(ProductTypeQuery.of());
        logger.info("queryAll service result : " + result);
    }

    public void queryByAttributeName() {
        QueryPredicate<ProductType> hasSizeAttribute = ProductTypeQueryModel.of().attributes().name().is("size");
        CompletionStage<PagedQueryResult<ProductType>> result = client.execute(ProductTypeQuery.of().withPredicates(hasSizeAttribute));
    }

    public void delete() {
        final DeleteCommand<ProductType> command = ProductTypeDeleteCommand.of(productType);
        final CompletionStage<ProductType> deletedProductType = client.execute(command);
    }   
    
    public PagedQueryResult<Product> getProductsForCategory()    		
            throws ExecutionException, InterruptedException {
    		ProductQuery query=null;
    		logger.info("CatalogTools : Fetching Products for categories from CT using Client.");
            return client.execute(query).toCompletableFuture().get();
        }
    
    /*public ShippingMethod getDefaultShippingMethod() {
        if (defaultShippingMethod == null) {
            defaultShippingMethod = client.execute(ShippingMethodQuery.of().byIsDefault())
                .getResults().get(0);
        }
        return defaultShippingMethod;
    }*/
}
