package com.miup.sg.productos.product_service.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.miup.sg.productos.product_service.models.common.dto.response.CustomResponse;
import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.dto.request.ProductCreateRequest;
import com.miup.sg.productos.product_service.models.productos.dto.request.ProductUpdateRequest;
import com.miup.sg.productos.product_service.models.productos.dto.response.ProductResponse;
import com.miup.sg.productos.product_service.models.productos.mapper.ProductToProductResponseMapper;
import com.miup.sg.productos.product_service.services.ProductCreateService;
import com.miup.sg.productos.product_service.services.ProductDeleteService;
import com.miup.sg.productos.product_service.services.ProductReadService;
import com.miup.sg.productos.product_service.services.ProductUpdateService;

@RestController
@RequestMapping("/v1/productos")
@RequiredArgsConstructor
@Validated
public class ProductController {

    @Autowired
    private ProductCreateService productCreateService;
    
    @Autowired
    private ProductReadService productReadService;
    
    @Autowired
    private ProductUpdateService productUpdateService;
    
    @Autowired
    private ProductDeleteService productDeleteService;

    private final ProductToProductResponseMapper productToProductResponseMapper = ProductToProductResponseMapper.initialize();


    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<String> createProduct(@RequestBody @Valid final ProductCreateRequest productCreateRequest) {

        final Product createdProduct = productCreateService
                .createProduct(productCreateRequest);

        return CustomResponse.successOf(createdProduct.getId());
    }

    @GetMapping("/lista")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Page<Product> findAll(Pageable pageable){
        return productReadService.findAll(pageable);
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<ProductResponse> getProductById(@PathVariable @UUID final String productId) {

        final Product product = productReadService.getProductById(productId);

        final ProductResponse productResponse = productToProductResponseMapper.map(product);

        return CustomResponse.successOf(productResponse);

    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<ProductResponse> updatedProductById(
            @RequestBody @Valid final ProductUpdateRequest productUpdateRequest,
            @PathVariable @UUID final String productId) {

        final Product updatedProduct = productUpdateService.updateProductById(productId, productUpdateRequest);

        final ProductResponse productResponse = productToProductResponseMapper.map(updatedProduct);

        return CustomResponse.successOf(productResponse);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<Void> deleteProductById(@PathVariable @UUID final String productId) {

        productDeleteService.deleteProductById(productId);
        return CustomResponse.SUCCESS;
    }

}