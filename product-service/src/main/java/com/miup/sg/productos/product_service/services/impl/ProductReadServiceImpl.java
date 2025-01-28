package com.miup.sg.productos.product_service.services.impl;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.miup.sg.productos.product_service.exception.ProductNotFoundException;
import com.miup.sg.productos.product_service.models.common.CustomPage;
import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.dto.request.ProductPagingRequest;
import com.miup.sg.productos.product_service.models.productos.entity.ProductEntity;
import com.miup.sg.productos.product_service.models.productos.mapper.ListProductEntityToListProductMapper;
import com.miup.sg.productos.product_service.models.productos.mapper.ProductEntityToProductMapper;
import com.miup.sg.productos.product_service.repositories.ProductRepository;
import com.miup.sg.productos.product_service.services.ProductReadService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReadServiceImpl implements ProductReadService {

    @Autowired
    private ProductRepository productRepository;

    private final ProductEntityToProductMapper productEntityToProductMapper = ProductEntityToProductMapper.initialize();

    private final ListProductEntityToListProductMapper listProductEntityToListProductMapper =
            ListProductEntityToListProductMapper.initialize();

    @Override
    public Product getProductById(String productId) {

        final ProductEntity productEntityFromDB = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("With given productID = " + productId));

        return productEntityToProductMapper.map(productEntityFromDB);
    }

    @Override
    public CustomPage<Product> getProducts(ProductPagingRequest productPagingRequest) {

        Page<ProductEntity> productEntityPage = productRepository.findAll(productPagingRequest.toPageable());

        if (productEntityPage.getContent().isEmpty()) {
            throw new ProductNotFoundException("Couldn't find any Product");
        }

        final List<Product> productDomainModels = listProductEntityToListProductMapper
                .toProductList(productEntityPage.getContent());

        return CustomPage.of(productDomainModels, productEntityPage);

    }

}