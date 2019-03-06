package com.myretail;

import com.myretail.service.ProductService;
import com.myretail.service.beans.ProductSO;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductService productService;
    public static String jsonString = "{\"product\": {\"item\": {\"product_description\": {\"title\": \"Hells Kitchen Season 6\"}}}}";

    @Test
    public void extractProductNameTest() throws JSONException {
        ProductSO productSO = productService.extractProductName(jsonString);
        assertEquals("Hells Kitchen Season 6",productSO.getName());
    }

}