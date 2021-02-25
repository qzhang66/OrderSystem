package com.ordersystem.sell.enums;


import lombok.Getter;

@Getter
public enum ProductStatusEnum {
    UP(0,"on stock"),
    DOWN(1,"off stock");

    private int code;
    private String message;

    ProductStatusEnum(int code, String message){
        this.code = code;
        this.message = message;
    }


}
