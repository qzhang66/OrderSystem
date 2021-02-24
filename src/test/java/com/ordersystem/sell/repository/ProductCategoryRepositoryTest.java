package com.ordersystem.sell.repository;

import com.ordersystem.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    @Transactional
    public void saveTest(){
//        ProductCategory productCategory = repository.findOne(2);
//        productCategory.setCategoryType(3);
//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setCategoryId(2);
//        productCategory.setCategoryName("men favorite");
//        productCategory.setCategoryType(3);
        ProductCategory productCategory = new ProductCategory("MAN FAVORITE", 4);

         ProductCategory result = repository.save(productCategory);
         Assert.assertNotEquals(null, result);

    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list  = Arrays.asList(2,3);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }



}