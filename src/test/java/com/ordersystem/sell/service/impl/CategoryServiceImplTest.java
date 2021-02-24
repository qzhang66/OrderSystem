package com.ordersystem.sell.service.impl;

import com.ordersystem.sell.dataobject.ProductCategory;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest extends TestCase {
    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOneTest()throws Exception{
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }
    @Test
    public void findAllTest() throws Exception{
        List<ProductCategory> list = categoryService.findAll();
        Assert.assertNotEquals(0,list.size());
    }
    @Test
    public void findByCategoryTypeInTest() throws Exception{
        List<Integer> list = Arrays.asList(2,3);
        List<ProductCategory> result = categoryService.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());

    }

    @Test
    public void saveTest() throws Exception{
        ProductCategory productCategory = new ProductCategory("Men favorite",4);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);

    }



}