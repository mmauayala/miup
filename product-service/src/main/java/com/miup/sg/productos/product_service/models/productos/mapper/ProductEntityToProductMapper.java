package com.miup.sg.productos.product_service.models.productos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.miup.sg.productos.product_service.models.common.mapper.BaseMapper;
import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.entity.ProductEntity;

@Mapper
public interface ProductEntityToProductMapper extends BaseMapper<ProductEntity, Product> {

    @Override
    Product map(ProductEntity source);

    static ProductEntityToProductMapper initialize() {
        return Mappers.getMapper(ProductEntityToProductMapper.class);
    }

}