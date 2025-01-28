package com.miup.sg.productos.product_service.services;


import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.dto.request.ProductUpdateRequest;

public interface ProductUpdateService {

    Product updateProductById(final String productId, final ProductUpdateRequest productUpdateRequest);

}


