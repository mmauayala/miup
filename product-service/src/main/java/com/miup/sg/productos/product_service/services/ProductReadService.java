package com.miup.sg.productos.product_service.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.dto.response.ProductoIdResponse;

public interface ProductReadService{

    Product getProductById(final String productId);

    Page<Product> findAll(Pageable pageable);

    ProductoIdResponse getProductIdByName(String name);


}