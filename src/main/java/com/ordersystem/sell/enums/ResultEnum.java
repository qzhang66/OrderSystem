package com.ordersystem.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PARAM_ERROR(1,"prameter is not correct"),
    PRODUCT_NOT_EXIST(10, "product doesn't exist"),
    PRODUCT_STOCK_ERROR(11, "Incorrect product stock"),
    ORDER_NOT_EXIST(12,"Order does not exist"),
    ORDERDETAIL_NOT_EXIST(13,"Order detail does not exist"),
    ORDER_STATUS_ERROR(14,"Order Status is not correct"),
    ORDER_UPDATE_FAIL(15," order unsuccessfully update"),
    ORDER_DETAIL_EMPTY(16,"Order detail is empty"),
    ORDER_PAY_STATUS_ERROR(17,"Pay status is not correct"),
    CART_EMPTY(18,"cart is empty")
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}