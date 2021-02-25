package com.ordersystem.sell.service.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ordersystem.sell.dataobject.ProductInfo;
import com.ordersystem.sell.enums.ProductStatusEnum;
import com.ordersystem.sell.service.ProductService;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest

public class ProductServiceImplTest extends TestCase {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void testFindOne() throws Exception{
        ProductInfo result = productService.findOne("123456");
        Assert.assertEquals("123456", result.getProductId());
    }

    @Test
    public void testFindUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());

    }

    @Test
    public void testFindAll() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
       // System.out.println(productInfoPage.getTotalElements());
        Assert.assertNotEquals(0,productInfoPage.getTotalElements());

    }

    @Test
    public void testSave() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("Rice with chicken");
        productInfo.setProductPrice(new BigDecimal(7.99));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("very good combo");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(3);
        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);

    }
}