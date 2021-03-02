package com.ordersystem.sell.service;

import com.ordersystem.sell.dataobject.ProductInfo;
import com.ordersystem.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);
    // find all on stock product
    List<ProductInfo> findUpAll();
    //find all product
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    void increaseStock(List<CartDTO> cartDTOList);
    void decreaseStock(List<CartDTO> cartDTOList);

}
