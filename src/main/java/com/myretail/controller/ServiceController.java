package com.myretail.controller;


import com.myretail.manager.ProductManager;
import com.myretail.manager.beans.ProductBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.myretail.exception.NotFoundException;

@RestController
public class ServiceController {


    Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    ProductManager productManager;

    /**
     * This method is to interpret the error and provide a custom error message.
     * @param ex exception to be handled.
     * @return ResponseEntity<ErrorResponse> This the HTTP response entity object with custom error response object.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {

        ErrorResponse error = new ErrorResponse();
        if (ex instanceof  IllegalArgumentException ){
            error.setErrorCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage (ex.getMessage());
        }else if (ex instanceof ChangeSetPersister.NotFoundException){
            error.setErrorCode(HttpStatus.NOT_FOUND.value());
            error.setMessage (ex.getMessage());
        }else{
            error.setErrorCode(500);
            error.setMessage (" Unknown error occured. Contact support center");
        }
        error.setMessage (ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method is a HTTP GET method.
     * It is used to get the product details and price for a given productId.
     * @param productId Get details of the product identified by this parameter.
     * @return ProductBO Business object that has the product name and price details.
     * @throws Exception Generic exception.
     */
    @RequestMapping(method=RequestMethod.GET, value="/product/{productId}")
    public ProductBO getProductDetails(@PathVariable("productId") Integer productId) throws Exception {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("The 'productId' parameter must not be null or empty or <=0");
        }
        ProductBO bo = productManager.getDetailsByProductId(productId);
        if ((bo.getId() == null) || (bo.getId() == 0)){
            throw new NotFoundException("The 'productId' Not found");
        }
        return bo;
    }

    /**
     * This method is a HTTP put method.
     * It is used to update the price of the product for a given productId.
     * @param productId Id of the product to be updated.
     * @param price Price to be updated.
     * @return boolean Updated successfully or not.
     * @throws Exception Generic exception.
     */
    @RequestMapping(method=RequestMethod.PUT, value="/product/{productId}")
    public boolean updateProductPrice(@PathVariable("productId") Integer productId, @RequestBody ProductBO price) throws Exception{

        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("The 'productId' parameter must not be null or empty or <=0");
        }
        if(price.getCurrentPrice().getPrice() <=0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        return productManager.updateProductPrice(productId, price.getCurrentPrice().getPrice());
    }

}
