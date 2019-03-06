package com.myretail.manager;


import com.mongodb.MongoException;
import com.mongodb.MongoQueryException;
import com.myretail.dao.PriceRepository;
import com.myretail.dao.beans.Price;
import com.myretail.manager.beans.CurrentPrice;
import com.myretail.manager.beans.ProductBO;
import com.myretail.service.ProductService;
import com.myretail.service.beans.ProductSO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for performing the business logic and data integration.
 */

@Component
public class ProductManager {
    Logger logger = LoggerFactory.getLogger(ProductManager.class);

    @Autowired
    PriceRepository priceRepository;
    @Autowired
    ProductService productService;
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * This method calls price repository and gets the price data from the database.
     * It also calls product service to fetch the product name.
     * It merges the data from both the data sources and provides ProductBO object.
     *
     * @param productId Get details of the product identified by this parameter.
     * @return ProductBO ProductBO
     * @throws Exception Generic Exception
     */
    public ProductBO getDetailsByProductId(Integer productId) throws Exception {
        Price priceDetails = priceRepository.findById(productId);
        ProductSO productName = productService.getProductDetailsFromService(productId);
        return populateProductObject(priceDetails, productName);
    }

    /**
     * This method calls the DAO layer method to update the price of given productId.
     *
     * @param productId Product id to update
     * @param price Price to update
     * @return boolean Updated successfully or not.
     * @throws Exception Generic exception.
     */
    public boolean updateProductPrice(Integer productId, Double price) throws Exception {
        Query query = new Query(Criteria.where("id").is(productId));
        Price newPrice = mongoTemplate.findAndModify(query, Update.update("price", price), Price.class);
        if(newPrice == null){
            throw new NullPointerException("Unable to find the ID");
        }
        return true;
    }


    /**
     * This method performs merge logic on PriceDO and ProductSO and returns ProductBO.
     * @param priceDetails Data object that has price details for the product.
     * @param productName Service object that has the product name.
     * @return ProductBO Business object that is integrated from DO and SO.
     */
    public ProductBO populateProductObject(Price priceDetails, ProductSO productName) {
        ProductBO productDetails = new ProductBO();
        productDetails.setId(priceDetails.getId());
        productDetails.setName(productName.getName());
        CurrentPrice cp = new CurrentPrice();
        cp.setCurrency(priceDetails.getCurrency());
        cp.setPrice(priceDetails.getPrice());
        productDetails.setCurrentPrice(cp);
        return productDetails;
    }
}
