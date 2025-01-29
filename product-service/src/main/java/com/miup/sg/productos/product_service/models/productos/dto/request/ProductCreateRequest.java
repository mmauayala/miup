package com.miup.sg.productos.product_service.models.productos.dto.request;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequest {

    private String name;

    private String medida;


}