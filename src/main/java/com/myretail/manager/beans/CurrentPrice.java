package com.myretail.manager.beans;

/**
 * This class represents the price and the currency of the product.
 */
public class CurrentPrice {

    private Double price;
    private String currency;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
