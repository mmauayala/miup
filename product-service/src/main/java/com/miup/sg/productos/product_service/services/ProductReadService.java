package com.miup.sg.productos.product_service.services;


import com.miup.sg.productos.product_service.models.common.CustomPage;
import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.dto.request.ProductPagingRequest;

public interface ProductReadService{

    Product getProductById(final String productId);

    CustomPage<Product> getProducts(final ProductPagingRequest productPagingRequest);

}