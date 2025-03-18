package com.miup.sg.productos.product_service.models.productos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.miup.sg.productos.product_service.models.common.mapper.BaseMapper;
import com.miup.sg.productos.product_service.models.productos.dto.request.ProductUpdateRequest;
import com.miup.sg.productos.product_service.models.productos.entity.ProductEntity;

@Mapper
public interface ProductUpdateRequestToProductEntityMapper extends BaseMapper<ProductUpdateRequest, ProductEntity> {

    
    @Named("mapForUpdating")
    default void mapForUpdating(ProductEntity productEntityToBeUpdate, ProductUpdateRequest productUpdateRequest) {
        productEntityToBeUpdate.setName(productUpdateRequest.getName());
        productEntityToBeUpdate.setMedida(productUpdateRequest.getMedida());
    }

    static ProductUpdateRequestToProductEntityMapper initialize() {
        return Mappers.getMapper(ProductUpdateRequestToProductEntityMapper.class);
    }

}