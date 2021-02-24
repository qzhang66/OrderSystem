package com.ordersystem.sell.repository;

import com.ordersystem.sell.dataobject.ProductInfo;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ProductInfoRepositoryTest extends TestCase {
    @Autowired
    private ProductInfoRepository repository;
    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("Rice with shrimp");
        productInfo.setProductPrice(new BigDecimal(8.99));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("very good dish");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(3);
        ProductInfo result = repository.save(productInfo);
        System.out.println("From here" + result);
        Assert.assertNotNull(result);

    }


    @Test
    public void testFindByProductStatus() throws Exception{
       List<ProductInfo> result  =repository.findByProductStatus(0);
       Assert.assertNotEquals(0,result.size());

    }
}