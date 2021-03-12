package com.ordersystem.sell.service.impl;

import com.ordersystem.sell.dto.OrderDTO;
import com.ordersystem.sell.enums.ResultEnum;
import com.ordersystem.sell.exception.SellException;
import com.ordersystem.sell.service.BuyerService;
import com.ordersystem.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid,orderId);
        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
      OrderDTO orderDTO =  checkOrderOwner(openid, orderId);
      if(orderDTO == null){
          log.error("cancel order can't find order, orderId={}", orderId);
          throw new SellException(ResultEnum.ORDER_NOT_EXIST);
      }
      return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId){
        OrderDTO orderDTO= orderService.findOne(orderId);
        if(orderDTO == null){
            return null;
        }
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("check order inconsistent openid in the order.openid={}, orderDTO={}", openid,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
