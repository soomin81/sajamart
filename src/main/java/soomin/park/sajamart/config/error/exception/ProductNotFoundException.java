package soomin.park.sajamart.config.error.exception;


import soomin.park.sajamart.config.error.ErrorCode;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException() {
        super(ErrorCode.PRODUCT_NOT_FOUND);
    }
}