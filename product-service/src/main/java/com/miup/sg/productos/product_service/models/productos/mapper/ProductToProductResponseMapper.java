package com.miup.sg.productos.product_service.models.productos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.miup.sg.productos.product_service.models.common.mapper.BaseMapper;
import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.dto.response.ProductResponse;

@Mapper
public interface ProductToProductResponseMapper extends BaseMapper<Product, ProductResponse> {

    @Override
    ProductResponse map(Product source);

    static ProductToProductResponseMapper initialize() {
        return Mappers.getMapper(ProductToProductResponseMapper.class);
    }

}