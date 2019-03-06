package com.myretail;

import com.myretail.dao.PriceRepository;
import com.myretail.dao.beans.Price;
import com.myretail.manager.ProductManager;
import com.myretail.manager.beans.ProductBO;
import com.myretail.service.ProductService;
import com.myretail.service.beans.ProductSO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductManagerTest {

    @InjectMocks
    private ProductManager productManager;
    @Mock
    private PriceRepository priceRepoitory;
    @Mock private ProductService productService;
    public static Integer id = 13860428;

    @Test
    public void getProductDetailsTest() throws Exception{
        Price priceDO = new Price();
        priceDO.setCurrency("USD");
        priceDO.setPrice(40.4);
        priceDO.setId(id);
        ProductSO productSO = new ProductSO();
        productSO.setName("Hells Kitchen Season 6");
        System.out.println("##################");
        Mockito.when(productService.getProductDetailsFromService(id)).thenReturn(productSO);
        System.out.println("++++++++++++++++++");
        Mockito.when(priceRepoitory.findById(id)).thenReturn(priceDO);
        System.out.println("******************");
        ProductBO productBO= productManager.getDetailsByProductId(id);
        assertEquals(priceDO.getId(), productBO.getId());
        assertEquals(priceDO.getCurrency(), productBO.getCurrentPrice().getCurrency());
        assertEquals(priceDO.getPrice(), productBO.getCurrentPrice().getPrice());
        assertEquals(productSO.getName(),productBO.getName());

    }

    @Test
    public void populateProductBOTest(){
        Price priceDO = new Price();
        priceDO.setCurrency("USD");
        priceDO.setPrice(100.4);
        priceDO.setId(id);
        ProductSO productSO = new ProductSO();
        productSO.setName("Prison Break");

        ProductBO productBO = productManager.populateProductObject(priceDO,productSO);

        assertEquals(priceDO.getId(), productBO.getId());
        assertEquals(priceDO.getCurrency(), productBO.getCurrentPrice().getCurrency());
        assertEquals(priceDO.getPrice(), productBO.getCurrentPrice().getPrice());
        assertEquals(productSO.getName(),productBO.getName());

    }
}