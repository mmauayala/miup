package com.miup.sg.productos.product_service.services;

import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.dto.request.ProductCreateRequest;

public interface ProductCreateService {
    
    Product createProduct(final ProductCreateRequest productCreateRequest);

}