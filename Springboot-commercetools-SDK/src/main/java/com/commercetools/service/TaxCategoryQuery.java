package com.commercetools.service;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.products.attributes.AttributeType;
import io.sphere.sdk.products.attributes.EnumAttributeType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.taxcategories.TaxCategory;
//import io.sphere.sdk.taxcategories.queries.TaxCategoryQuery;

import java.util.List;
import java.util.concurrent.CompletionStage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.commercetools.controllers.CartController;

@Service
public class TaxCategoryQuery {
	private static final Logger logger = Logger.getLogger(TaxCategoryQuery.class);
    SphereClient client;

  /*  public void exampleQuery() {
    	final Cart cart = client.executeBlocking(CartCreateCommand.of(CartDraft.of(EUR)));
        CompletionStage<PagedQueryResult<TaxCategory>> future = client.execute(TaxCategoryQuery.of().byName("de19"));
    }
    */
    
    public void queryByNameExample() {
    	logger.info("queryByNameExample : ");
        CompletionStage<PagedQueryResult<ProductType>> queryResultFuture = client.execute(ProductTypeQuery.of().byName("MensTshirts"));
        logger.info("queryResultFuture : "+queryResultFuture);
        CompletionStage<List<EnumValue>> possibleValuesFuture = queryResultFuture.thenApply(
                queryResult -> extractPossibleEnumValuesForSize(queryResult));
        logger.info("possibleValuesFuture : "+possibleValuesFuture);
    }
    
    
    private static List<EnumValue> extractPossibleEnumValuesForSize(PagedQueryResult<ProductType> pagedQueryResult) {
    	System.out.println("extractPossibleEnumValuesForSize : ");
    	ProductType productType = pagedQueryResult.
                head().
                orElseThrow(MissingProductTypeException::new);
        System.out.println("productType : "+productType);
        AttributeDefinition sizeAttribute = productType.
                findAttribute("size").
                orElseThrow(MissingAttributeException::new);
        System.out.println("sizeAttribute : "+sizeAttribute);
        if (sizeAttribute.getAttributeType() instanceof EnumAttributeType) {
            return ((EnumAttributeType) sizeAttribute.getAttributeType()).getValues();
        } else {
            throw new UnexpectedAttributeTypeException("size", EnumAttributeType.class, sizeAttribute.getAttributeType());
        }
    }
    
    
    public static class MissingProductTypeException extends RuntimeException {
        private static final long serialVersionUID = 4954918890077093840L;
    }

    public static class MissingAttributeException extends RuntimeException {
        private static final long serialVersionUID = 4954918890077093841L;
    }
    
    
    public static class UnexpectedAttributeTypeException extends RuntimeException {
        private static final long serialVersionUID = 4954918890077093842L;

        private final String attributeName;
        private final Class<? extends AttributeType> expectedType;
        private final AttributeType actualType;

        public UnexpectedAttributeTypeException(String attributeName, Class<? extends AttributeType> expectedType, AttributeType actualType) {
            super("Unexpected type of attribute with name '" + attributeName + "'. Expected type is '" +
                    expectedType.getSimpleName() + "', but actual type was '" + actualType + "'.");

            this.attributeName = attributeName;
            this.expectedType = expectedType;
            this.actualType = actualType;
        }

        public String getAttributeName() {
            return attributeName;
        }

        public Class<? extends AttributeType> getExpectedType() {
            return expectedType;
        }

        public AttributeType getActualType() {
            return actualType;
        }
    }
}
