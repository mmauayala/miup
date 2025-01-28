package com.miup.sg.productos.product_service.models.productos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.miup.sg.productos.product_service.models.productos.Product;
import com.miup.sg.productos.product_service.models.productos.entity.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ListProductEntityToListProductMapper {

    ProductEntityToProductMapper productEntityToProductMapper = Mappers.getMapper(ProductEntityToProductMapper.class);

    default List<Product> toProductList(List<ProductEntity> productEntities) {

        if (productEntities == null) {
            return null;
        }

        return productEntities.stream()
                .map(productEntityToProductMapper::map)
                .collect(Collectors.toList());

    }

    static ListProductEntityToListProductMapper initialize() {
        return Mappers.getMapper(ListProductEntityToListProductMapper.class);
    }

}