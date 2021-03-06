package com.ordersystem.sell.service.impl;

import com.ordersystem.sell.dataobject.OrderDetail;
import com.ordersystem.sell.dto.OrderDTO;
import com.ordersystem.sell.enums.OrderStatusEnum;
import com.ordersystem.sell.enums.PayStatusEnum;
import com.ordersystem.sell.repository.OrderDetailRepositoryTest;
import com.ordersystem.sell.repository.OrderMasterRepository;
import com.ordersystem.sell.service.OrderService;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final String ORDER_ID = "1614720614347968471" ;

    @Test
    public void testCreate() throws Exception {
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

    @Test
    public void testFindOne() throws Exception{
       OrderDTO result = orderService.findOne(ORDER_ID);
       log.info("find single order result = {}", result);
       Assert.assertEquals(ORDER_ID, result.getOrderId());


    }

    @Test
    public void testFindList() throws Exception{
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void testCancel() throws Exception{
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    public void testFinish() throws Exception{
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISH.getCode(), result.getOrderStatus());

    }

    @Test
    public void testPaid() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());

    }
}