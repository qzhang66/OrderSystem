package com.ordersystem.sell.service;

import com.ordersystem.sell.dto.OrderDTO;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    // create order
    OrderDTO create (OrderDTO orderDTO);

    //find a single order
    OrderDTO findOne(String orderId);
    // find all orders
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);
    //cancel order
    OrderDTO cancel(OrderDTO orderDTO);
    // complete order
    OrderDTO finish (OrderDTO orderDTO);
    // pay order
    OrderDTO paid(OrderDTO orderDTO);
}
