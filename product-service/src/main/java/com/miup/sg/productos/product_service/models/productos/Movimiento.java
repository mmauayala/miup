package com.miup.sg.productos.product_service.models.productos;

import com.miup.sg.productos.product_service.models.common.BaseDomainModelMov;
import com.miup.sg.productos.product_service.models.productos.entity.ProductEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento extends BaseDomainModelMov {

    private Long id;
    private ProductEntity producto;
    private Double cantidad; 
    private Double precioCompra; 
    private String tipo; 


}