package com.miup.sg.productos.product_service.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miup.sg.productos.product_service.exception.ProductAlreadyExistException;
import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.dto.request.ProductCreateRequest;
import com.miup.sg.productos.product_service.models.productos.entity.ProductEntity;
import com.miup.sg.productos.product_service.models.productos.mapper.ProductCreateRequestToProductEntityMapper;
import com.miup.sg.productos.product_service.models.productos.mapper.ProductEntityToProductMapper;
import com.miup.sg.productos.product_service.repositories.ProductRepository;
import com.miup.sg.productos.product_service.services.ProductCreateService;


@Service
@RequiredArgsConstructor
public class ProductCreateServiceImpl implements ProductCreateService {

    @Autowired
    private ProductRepository productRepository;

    private final ProductCreateRequestToProductEntityMapper productCreateRequestToProductEntityMapper =
            ProductCreateRequestToProductEntityMapper.initialize();

    private final ProductEntityToProductMapper productEntityToProductMapper = ProductEntityToProductMapper.initialize();

    @Override
    public Product createProduct(ProductCreateRequest productCreateRequest) {

        checkUniquenessProductName(productCreateRequest.getName());

        final ProductEntity productEntityToBeSave = productCreateRequestToProductEntityMapper.mapForSaving(productCreateRequest);

        ProductEntity savedProductEntity = productRepository.save(productEntityToBeSave);

        return productEntityToProductMapper.map(savedProductEntity);

    }

    private void checkUniquenessProductName(final String productName) {
        if (productRepository.existsProductEntityByName(productName)) {
            throw new ProductAlreadyExistException("There is another product with given name: " + productName);
        }
    }

}