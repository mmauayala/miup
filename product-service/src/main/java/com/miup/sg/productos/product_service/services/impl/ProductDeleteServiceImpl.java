package com.miup.sg.productos.product_service.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miup.sg.productos.product_service.exception.ProductNotFoundException;
import com.miup.sg.productos.product_service.models.productos.entity.ProductEntity;
import com.miup.sg.productos.product_service.repositories.ProductRepository;
import com.miup.sg.productos.product_service.services.ProductDeleteService;

/**
 * Service implementation named {@link ProductDeleteServiceImpl} for deleting products.
 */
@Service
@RequiredArgsConstructor
public class ProductDeleteServiceImpl implements ProductDeleteService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void deleteProductById(String productId) {

        final ProductEntity productEntityToBeDelete = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("With given productID = " + productId));

        productRepository.delete(productEntityToBeDelete);

    }

}