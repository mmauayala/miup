package com.miup.sg.productos.product_service.models.productos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.miup.sg.productos.product_service.models.common.CustomPage;
import com.miup.sg.productos.product_service.models.common.dto.response.CustomPagingResponse;
import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.dto.response.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface CustomPageToCustomPagingResponseMapper {

    ProductToProductResponseMapper productToProductResponseMapper = Mappers.getMapper(ProductToProductResponseMapper.class);

    default CustomPagingResponse<ProductResponse> toPagingResponse(CustomPage<Product> productPage) {

        if (productPage == null) {
            return null;
        }

        return CustomPagingResponse.<ProductResponse>builder()
                .content(toProductResponseList(productPage.getContent()))
                .totalElementCount(productPage.getTotalElementCount())
                .totalPageCount(productPage.getTotalPageCount())
                .pageNumber(productPage.getPageNumber())
                .pageSize(productPage.getPageSize())
                .build();

    }

    default List<ProductResponse> toProductResponseList(List<Product> products) {

        if (products == null) {
            return null;
        }

        return products.stream()
                .map(productToProductResponseMapper::map)
                .collect(Collectors.toList());

    }

    static CustomPageToCustomPagingResponseMapper initialize() {
        return Mappers.getMapper(CustomPageToCustomPagingResponseMapper.class);
    }

}