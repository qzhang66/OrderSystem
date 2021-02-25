package com.ordersystem.sell.controller;

import com.ordersystem.sell.VO.ProductInfoVO;
import com.ordersystem.sell.VO.ProductVO;
import com.ordersystem.sell.VO.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @GetMapping("/list")
    public ResultVO list(){
        ResultVO resultVO = new ResultVO();
        ProductVO productVO = new ProductVO();
        ProductInfoVO productInfoVO = new ProductInfoVO();

        productVO.setProductInfoVOList(Arrays.asList(productInfoVO));
        resultVO.setCode(0);
        resultVO.setMsg("Success");
        resultVO.setData(Arrays.asList(productVO));

        return resultVO;
    }
}
