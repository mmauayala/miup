package com.miup.sg.productos.product_service.models.productos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.miup.sg.productos.product_service.models.common.mapper.BaseMapper;
import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.entity.ProductEntity;

@Mapper
public interface ProductToProductEntityMapper extends BaseMapper<Product, ProductEntity> {


    @Override
    ProductEntity map(Product source);

    static ProductToProductEntityMapper initialize() {
        return Mappers.getMapper(ProductToProductEntityMapper.class);
    }

}