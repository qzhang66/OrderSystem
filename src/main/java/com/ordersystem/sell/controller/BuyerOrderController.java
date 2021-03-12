package com.ordersystem.sell.controller;

import com.ordersystem.sell.VO.ResultVO;
import com.ordersystem.sell.converter.OrderForm2OrderDTO;
import com.ordersystem.sell.dto.OrderDTO;
import com.ordersystem.sell.enums.ResultEnum;
import com.ordersystem.sell.exception.SellException;
import com.ordersystem.sell.service.BuyerService;
import com.ordersystem.sell.service.OrderService;
import com.ordersystem.sell.utils.ResultVOUtil;
import com.ordersystem.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j

public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;


    @PostMapping("/create")
    // create order
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("create order parameter is not correct, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTO.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("cart can't be empty");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        return ResultVOUtil.success(map);

    }
    // orderMaster
    @GetMapping("/list")

    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value ="page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("check order list openid can not be empty");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid,request);

        return ResultVOUtil.success(orderDTOPage.getContent());

    }

    //order detail
    @GetMapping("/detail")

    public ResultVO<OrderDTO> detail (@RequestParam("openid") String openid,
                                          @RequestParam("orderid") String orderid){

        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderid);
       return ResultVOUtil.success(orderDTO);

    }
    // cancel order
    @PostMapping("/cancel")
    public ResultVO cancel (@RequestParam("openid") String openid,
                            @RequestParam("orderid") String orderid){

        buyerService.cancelOrder(openid,orderid);
        return ResultVOUtil.success();
    }
}
