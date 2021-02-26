package com.ordersystem.sell.controller;

import com.ordersystem.sell.VO.ProductInfoVO;
import com.ordersystem.sell.VO.ProductVO;
import com.ordersystem.sell.VO.ResultVO;
import com.ordersystem.sell.dataobject.ProductCategory;
import com.ordersystem.sell.dataobject.ProductInfo;
import com.ordersystem.sell.service.CategoryService;
import com.ordersystem.sell.service.ProductService;
import com.ordersystem.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list(){
       List<ProductInfo> productInfoList = productService.findUpAll();

       List<Integer> categoryTypeList = new ArrayList<>();
       for(ProductInfo productInfo: productInfoList){
           categoryTypeList.add(productInfo.getCategoryType());
       }
       List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

       List<ProductVO> productVOList = new ArrayList<>();

       for(ProductCategory productCategory: productCategoryList){
           ProductVO productVO = new ProductVO();
           productVO.setCategoryType(productCategory.getCategoryType());
           productVO.setCategoryName(productCategory.getCategoryName());

           List<ProductInfoVO> productInfoVOList = new ArrayList<>();
           for(ProductInfo productInfo:productInfoList){
              if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                  ProductInfoVO productInfoVO = new ProductInfoVO();
                  BeanUtils.copyProperties(productInfo,productInfoVO);
                  productInfoVOList.add(productInfoVO);
              }
           }
           productVO.setProductInfoVOList(productInfoVOList);
           productVOList.add(productVO);
       }



        return ResultVOUtil.success(productVOList);
    }
}
