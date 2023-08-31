package com.products.www.altentest.exceptions;

public class ProductAccessDeniedException extends RuntimeException{

    public ProductAccessDeniedException(String message) {
        super(message);
    }
}