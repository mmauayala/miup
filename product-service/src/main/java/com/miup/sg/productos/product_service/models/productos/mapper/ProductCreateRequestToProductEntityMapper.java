package com.miup.sg.productos.product_service.models.productos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.miup.sg.productos.product_service.models.common.mapper.BaseMapper;
import com.miup.sg.productos.product_service.models.productos.dto.request.ProductCreateRequest;
import com.miup.sg.productos.product_service.models.productos.entity.ProductEntity;

@Mapper
public interface ProductCreateRequestToProductEntityMapper extends BaseMapper<ProductCreateRequest, ProductEntity> {

    
    @Named("mapForSaving")
    default ProductEntity mapForSaving(ProductCreateRequest productCreateRequest) {
        return ProductEntity.builder()
                .medida(productCreateRequest.getMedida())
                .name(productCreateRequest.getName())
                .build();
    }

    static ProductCreateRequestToProductEntityMapper initialize() {
        return Mappers.getMapper(ProductCreateRequestToProductEntityMapper.class);
    }

}