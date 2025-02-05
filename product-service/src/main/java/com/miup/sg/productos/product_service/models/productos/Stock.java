package com.miup.sg.productos.product_service.models.productos;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    
    private Double cantidad;
    
    private Double precioIngreso;

}
