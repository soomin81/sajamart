package soomin.park.sajamart.config.error.exception;


import soomin.park.sajamart.config.error.ErrorCode;

public class ItemNotFoundException extends NotFoundException {
    public ItemNotFoundException() {
        super(ErrorCode.ITEM_NOT_FOUND);
    }
}