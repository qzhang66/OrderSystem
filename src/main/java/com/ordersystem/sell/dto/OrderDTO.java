package com.ordersystem.sell.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ordersystem.sell.dataobject.OrderDetail;
import com.ordersystem.sell.enums.OrderStatusEnum;
import com.ordersystem.sell.enums.PayStatusEnum;
import com.ordersystem.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    List<OrderDetail> orderDetailList;

}
