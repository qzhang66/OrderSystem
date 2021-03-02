package com.ordersystem.sell.dto;

import com.ordersystem.sell.dataobject.OrderDetail;
import com.ordersystem.sell.enums.OrderStatusEnum;
import com.ordersystem.sell.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus;
    /*pay status, not pay=0*/
    private Integer payStatus;
    private Date createTime;
    private Date updateTime;
    List<OrderDetail> orderDetailList;

}
