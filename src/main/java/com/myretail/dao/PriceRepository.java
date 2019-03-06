package com.myretail.dao;

import com.myretail.dao.beans.Price;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * This is the DAO layer to interact with the database and fetch the price and currency for the given product.
 */
public interface PriceRepository extends MongoRepository<Price, String> {
    Price findById(Integer id);
}
