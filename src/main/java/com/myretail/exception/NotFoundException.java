package com.myretail.exception;

@SuppressWarnings("serial")
public class NotFoundException extends Exception{

    public NotFoundException(String message){
        super(message);
    }
}
