package com.ordersystem.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ordersystem.sell.dataobject.OrderDetail;
import com.ordersystem.sell.dto.OrderDTO;
import com.ordersystem.sell.enums.ResultEnum;
import com.ordersystem.sell.exception.SellException;
import form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTO {
    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try{
            orderDetailList  = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());

        } catch (Exception e){
            log.error("Object convert error, string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }


}
