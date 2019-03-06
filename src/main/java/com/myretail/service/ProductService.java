package com.myretail.service;

import com.myretail.service.beans.ProductSO;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
* This class is responsible for performing a HTTP call to a third party API.
*/
@Service
@PropertySource("classpath:global.properties")
public class ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Value("${endpoint.url}")
    private String baseURI;
    @Value("${endpoint.suffix}")
    private String suffix;

    private static final String PRODUCT="product";
    private static final String ITEM="item";
    private static final String PRODUCT_DESCRIPTION="product_description";
    private static final String TITLE="title";


    private RestTemplate restTemplate = new RestTemplate();

    /**
     * This method performs a HTTP GET call to an external service and get product details in JSON fromat.
     * It responsible for converting the JSON string into an JSONObject and extracting the product name.
     *
     * @param productId
     * @return ProductSO
     * @throws JSONException
     */

    public ProductSO getProductDetailsFromService(Integer productId) throws JSONException {
        logger.info("" + productId);
        String endpointURL = constructEndpointURL(productId);
        String jsonResponse = restTemplate.getForObject(endpointURL, String.class);
        ProductSO productSO = extractProductName(jsonResponse);
        logger.info("" + jsonResponse);
        logger.info("" + productSO.getName());
        return productSO;
    }

    public ProductSO extractProductName(String jsonResponse) {
        ProductSO prSo = new ProductSO();
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONObject product = jsonObject.getJSONObject(PRODUCT);
        JSONObject item = product.getJSONObject(ITEM);
        JSONObject productDescription = item.getJSONObject(PRODUCT_DESCRIPTION);
        prSo.setName(productDescription.getString(TITLE));
        return prSo;
    }

    private String constructEndpointURL(Integer productId) {
        StringBuffer url = new StringBuffer(baseURI);
        url.append(productId);
        url.append(suffix);
        return String.valueOf(url);
    }
}
