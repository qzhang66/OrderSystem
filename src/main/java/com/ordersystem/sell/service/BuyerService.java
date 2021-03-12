package com.ordersystem.sell.service;

import com.ordersystem.sell.dto.OrderDTO;

public interface BuyerService {
    OrderDTO findOrderOne(String openid, String orderId);
    OrderDTO cancelOrder(String openid, String orderId);
}
