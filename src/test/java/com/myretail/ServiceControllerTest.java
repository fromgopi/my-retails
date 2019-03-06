package com.myretail;

import com.myretail.controller.ServiceController;
import com.myretail.manager.ProductManager;
import com.myretail.manager.beans.CurrentPrice;
import com.myretail.manager.beans.ProductBO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ServiceController.class)
public class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductManager productManager;
    private static Integer productId = 13860428;

    @Test
    public void getProductDetailsTest() throws Exception {
        ProductBO productBO = new ProductBO();
        productBO.setName("Hells Kitchen Season 6");
        productBO.setId(productId);
        CurrentPrice cp = new CurrentPrice();
        cp.setCurrency("USD");
        cp.setPrice(40.9);
        productBO.setCurrentPrice(cp);

        Mockito.when(productManager.getDetailsByProductId(productId)).thenReturn(productBO);

        RequestBuilder rb = MockMvcRequestBuilders.get("/product/13860428").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(rb).andReturn();
        System.out.println("-----"+ result.getResponse().getContentAsString());
        String expected = "{id:13860428,name:'Hells Kitchen Season 6',currentPrice:{price:40.9,currency:USD}}";

        JSONAssert.assertEquals(expected,result.getResponse().getContentAsString(), false);

    }

}
