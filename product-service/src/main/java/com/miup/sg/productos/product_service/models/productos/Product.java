package com.miup.sg.productos.product_service.models.productos;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

import com.miup.sg.productos.product_service.models.common.BaseDomainModel;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseDomainModel {

    private String id;
    private String name;
    private String medida;
    private List<Stock> stock;

}