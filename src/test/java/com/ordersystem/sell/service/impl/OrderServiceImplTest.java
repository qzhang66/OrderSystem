package com.ordersystem.sell.service.impl;

import com.ordersystem.sell.dataobject.OrderDetail;
import com.ordersystem.sell.dto.OrderDTO;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest  {

    @Autowired
    private OrderServiceImpl orderService;
    private final String BUYER_OPENID = "1101110";

    @Test
    public void testCreate() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("Alice");
        orderDTO.setBuyerAddress("Calgary,Canada");
        orderDTO.setBuyerPhone("403-321-7654");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123458");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("123456");
        o2.setProductQuantity(2);
        orderDetailList.add(o1);
        orderDetailList.add(o2);
        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
        log.info("create order result = {}",result);
        Assert.assertNotNull(result);
    }

    public void testFindOne() {
    }

    public void testFindList() {
    }

    public void testCancel() {
    }

    public void testFinish() {
    }

    public void testPaid() {
    }
}