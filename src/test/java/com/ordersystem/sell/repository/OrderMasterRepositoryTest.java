package com.ordersystem.sell.repository;

import com.ordersystem.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    OrderMasterRepository repository;
    private final String OPENID = "110110";
    @Test
    public void testSave(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerName("Jim");
        orderMaster.setBuyerPhone("4036689745");
        orderMaster.setBuyerAddress("1 masterStreet");
        orderMaster.setBuyerOpenid("110110");
        orderMaster.setOrderAmount(new BigDecimal(5.99));
        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);



    }
    @Test
    public void testFindByBuyerOpenid() throws Exception{
        PageRequest request = new PageRequest(1,3);
        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID,request);
        Assert.assertNotEquals(0,result.getTotalElements());
       // System.out.println("Here are"+result.getTotalElements());
    }
}