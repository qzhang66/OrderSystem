package com.ordersystem.sell.VO;

import lombok.Data;

@Data
public class ResultVO <T>{
    //error code
    private int code;
    private String msg;
    // return content
    private T data;
}
