package com.ordersystem.sell.service.impl;

import com.ordersystem.sell.converter.OrderMasterToOrderDTOConverter;
import com.ordersystem.sell.dataobject.OrderDetail;
import com.ordersystem.sell.dataobject.OrderMaster;
import com.ordersystem.sell.dataobject.ProductInfo;
import com.ordersystem.sell.dto.CartDTO;
import com.ordersystem.sell.dto.OrderDTO;
import com.ordersystem.sell.enums.OrderStatusEnum;
import com.ordersystem.sell.enums.PayStatusEnum;
import com.ordersystem.sell.enums.ResultEnum;
import com.ordersystem.sell.exception.SellException;
import com.ordersystem.sell.repository.OrderDetailRepository;
import com.ordersystem.sell.repository.OrderMasterRepository;
import com.ordersystem.sell.service.OrderService;
import com.ordersystem.sell.service.ProductService;
import com.ordersystem.sell.utils.KeyUtil;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Transactional
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        List<CartDTO> cartDTOList = new ArrayList<>();

        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        OrderMaster save = orderMasterRepository.save(orderMaster);


        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
       OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
       if(orderMaster == null ){
           throw new SellException(ResultEnum.ORDER_NOT_EXIST);
       }

       List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
       if(CollectionUtils.isEmpty(orderDetailList)){
           throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
       }

       OrderDTO orderDTO = new OrderDTO();
       BeanUtils.copyProperties(orderMaster, orderDTO);
       orderDTO.setOrderDetailList(orderDetailList);
       return orderDTO;

    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOList =
                OrderMasterToOrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
                log.error("cancel order order status is incorrect, orderId={},orderStatus={}", orderDTO.getOrderId(),orderDTO.getOrderStatus());
                throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
            }
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
                log.error("cancel order, unsuccessfully update orderMaster={}", orderMaster);
                throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
            }
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
                log.error("cancel order, no product detail in order, orderDTO={}",  orderDTO);
                throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
            }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e->new CartDTO(e.getProductId(),e.getProductQuantity()))
                                        .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
                //TODO
            }
            return orderDTO;

    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
       if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
           log.error("finish order. order status is not correct, orderId={}, orderStatus={}", orderDTO.getOrderId(),orderDTO.getOrderStatus());
           throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
       }
       orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
       OrderMaster orderMaster = new OrderMaster();
       BeanUtils.copyProperties(orderDTO,orderMaster);
       OrderMaster updateResult = orderMasterRepository.save(orderMaster);
       if(updateResult == null){
           log.error("finish order unsuccessfully updated orderMaster={}", orderMaster);
           throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
       }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("paid order unsuccessfully orderStatus is not correct, orderId={}, orderStatus={}", orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("pay order unsuccessfully paystatus is incorrect, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("Pay order unsuccessfully orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }
}
