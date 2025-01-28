package com.miup.sg.productos.product_service.models.productos.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequest {

    @Size(
            min = 1,
            message = "Product name can't be blank."
    )
    private String name;

    private String medida;


}