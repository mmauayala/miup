package com.miup.sg.productos.product_service.services.impl;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miup.sg.productos.product_service.exception.ProductAlreadyExistException;
import com.miup.sg.productos.product_service.exception.ProductNotFoundException;
import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.dto.request.ProductUpdateRequest;
import com.miup.sg.productos.product_service.models.productos.entity.ProductEntity;
import com.miup.sg.productos.product_service.models.productos.mapper.ProductEntityToProductMapper;
import com.miup.sg.productos.product_service.models.productos.mapper.ProductUpdateRequestToProductEntityMapper;
import com.miup.sg.productos.product_service.repositories.ProductRepository;
import com.miup.sg.productos.product_service.services.ProductUpdateService;

@Service
@RequiredArgsConstructor
public class ProductUpdateServiceImpl implements ProductUpdateService {

    @Autowired
    private ProductRepository productRepository;

    private final ProductUpdateRequestToProductEntityMapper productUpdateRequestToProductEntityMapper =
            ProductUpdateRequestToProductEntityMapper.initialize();

    private final ProductEntityToProductMapper productEntityToProductMapper =
            ProductEntityToProductMapper.initialize();

    @Override
    public Product updateProductById(String productId, ProductUpdateRequest productUpdateRequest) {

        checkProductNameUniqueness(productUpdateRequest.getName());

        final ProductEntity productEntityToBeUpdate = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("With given productID = " + productId));

        productUpdateRequestToProductEntityMapper.mapForUpdating(productEntityToBeUpdate, productUpdateRequest);

        ProductEntity updatedProductEntity = productRepository.save(productEntityToBeUpdate);

        return productEntityToProductMapper.map(updatedProductEntity);

    }


    private void checkProductNameUniqueness(final String productName) {
        if (productRepository.existsProductEntityByName(productName)) {
            throw new ProductAlreadyExistException("With given product name = " + productName);
        }
    }

}